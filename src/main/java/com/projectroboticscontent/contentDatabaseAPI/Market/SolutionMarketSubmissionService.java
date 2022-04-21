package com.projectroboticscontent.contentDatabaseAPI.Market;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.PublicFiles.PublicFileStorage;
import com.projectroboticscontent.contentDatabaseAPI.TransactionHistory.AddTransactionServiceStatic;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import com.projectroboticscontent.contentDatabaseAPI.Utility.File;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolutionMarketSubmissionService {

    private final MongoClient client;
    private final ReactiveMongoTemplate MongoOps;

    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;

    public SolutionMarketSubmissionService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testSolutionDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }

    private ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Submission Error\n");
    }

    private Mono<Exception> submissionError(){

        return Mono.just(createStatusException());

    }




    private Mono<String> submissionSuccess ()
    {
        return Mono.just("Submission was successful");
    }

    private Flux<Document> newFileSubmission(PublicFileStorage[] FILE_LIST, long FORM_ID, String FILE_OWNER) {

        List<Document> documents = new ArrayList<Document>();
        Gson gson = new Gson();


        List<Document> DATA_DOCUMENTS = new ArrayList<Document> ();


        for (int i = 0; i < FILE_LIST.length; i++) {

            long PRIMARY_KEY = JwtTokenUtil.parseJWT(FILE_LIST[i].getToken(), FILE_LIST[i].getUSERTYPE());

            long FILE_ID = System.currentTimeMillis() + i;

            FILE_LIST[i].setFILE_ID(FILE_ID);

            FILE_LIST[i].setPRIMARY_KEY(PRIMARY_KEY);

            FILE_LIST[i].setFORM_ID(FORM_ID);

            FILE_LIST[i].setFILE_OWNER(FILE_OWNER);


            //documents.add(Document.parse(gson.toJson(FILE_LIST[i])));

            DATA_DOCUMENTS.add(Document.parse(gson.toJson(new File(FILE_LIST[i].getBINARY_FILE_DATA(), FILE_LIST[i].getFILE_ID()))));

            FILE_LIST[i].setBINARY_FILE_DATA(Hashing.sha256()
                    .hashString(FILE_LIST[i].getBINARY_FILE_DATA(), StandardCharsets.UTF_8)
                    .toString());

            documents.add(Document.parse(gson.toJson(FILE_LIST[i])));


        }


        Mono<List<Document>> list = Mono.just(documents);

        Mono<List<Document>> list2 = Mono.just(DATA_DOCUMENTS);


        return MongoOps.insertAll(list, "SolutionFileCollection")
                .flatMap(output -> {
                            return MongoOps.insertAll(list2, "SolutionFileCollection");
                        }
                );
    }







    private Mono<String> newMarketSolution(String SOLUTION_OWNER, String SOLUTION_TITLE,
                                           long STATUS, long SOLUTION_ID,
                                           long SOLUTION_PROVIDER_PRIMARY_KEY,
                                           String SOLUTION_PROVIDER_WALLET_ID,
                                           String JSON_SOLUTION_DATA,
                                           String token, String USERTYPE, long PRICE)
    {

        final String DATETIME = LocalDateTime.now().toString();

        final String url = Configuration.blockchainIPAddress + "/sol/set";

        WebClient addBlockClient = WebClient.create();

        final SolutionMarketSubmission submission = new SolutionMarketSubmission( SOLUTION_OWNER, SOLUTION_TITLE,
         STATUS, SOLUTION_ID,
         SOLUTION_PROVIDER_PRIMARY_KEY,
         SOLUTION_PROVIDER_WALLET_ID,  DATETIME,
               JSON_SOLUTION_DATA,
             token,  USERTYPE,  PRICE);

        return MongoOps.insert(submission,"SolutionProviderCollection")
                .flatMap(out1 ->{
                    return addBlockClient
                            .get()
                            .uri(url+"?key=" + Long.toString(SOLUTION_ID) + "&value="
                                    +out1.getHASH())
                            .retrieve()
                            .bodyToMono(String.class);
                })
                .flatMap(out2 ->{
                    return submissionSuccess();
                })
                .switchIfEmpty(Mono.just("error"));


    }

    private Flux<Document> newMarketSolution(String SOLUTION_OWNER, String SOLUTION_TITLE,
                                   long STATUS, long SOLUTION_ID,
                                   long SOLUTION_PROVIDER_PRIMARY_KEY,
                                   String SOLUTION_PROVIDER_WALLET_ID,
                                    String JSON_SOLUTION_DATA,
                                   String token, String USERTYPE, long PRICE,PublicFileStorage[] FILE_LIST)
    {

        final String DATETIME = LocalDateTime.now().toString();

        final SolutionMarketSubmission submission = new SolutionMarketSubmission( SOLUTION_OWNER, SOLUTION_TITLE,
                STATUS, SOLUTION_ID,
                SOLUTION_PROVIDER_PRIMARY_KEY,
                SOLUTION_PROVIDER_WALLET_ID,  DATETIME,
                 JSON_SOLUTION_DATA,
                token,  USERTYPE,  PRICE);

        return MongoOps.insert(submission,"SolutionProviderCollection")
                .flatMapMany(output ->{
                    // return FileStorageService.newFile( MongoOps, "newFile", "pdf", "A", token, USERTYPE,
                    //         PRIMARY_KEY, FORM_ID);
                    return  newFileSubmission(FILE_LIST, SOLUTION_ID, SOLUTION_OWNER );
                });


        /*
        return  FileStorageService.newFileSubmission(FILE_LIST, MongoOps)
                .flatMap(output->{
                    return MongoOps.insert(submission,"UserRequestCollection");
                });

         */

    }


    public Mono<String> newMarketSolutionSubmission(String token, String USERTYPE,
                                      String SOLUTION_TITLE, String JSON_SOLUTION_DATA,
                                                    long PRICE,  long STATUS)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long SOLUTION_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));

        // query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output ->{


                    return newMarketSolution(  output.getFIRSTNAME()+ " " + output.getLASTNAME(),
                             SOLUTION_TITLE, STATUS, SOLUTION_ID,
                    PRIMARY_KEY,
                    output.getWALLET_ID(),
                      JSON_SOLUTION_DATA,
                             token,  USERTYPE, PRICE);
                })
                .switchIfEmpty(Mono.just("error"));

        //.switchIfEmpty(submissionError());


    }

    public Flux<Document> newMarketSolutionSubmission(String token, String USERTYPE,
                                                      String SOLUTION_TITLE, String JSON_SOLUTION_DATA,
                                                      long PRICE, long STATUS,
                                        PublicFileStorage[] FILE_LIST)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long SOLUTION_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));

        // query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMapMany(output ->{
                    return newMarketSolution(output.getFIRSTNAME()+ " " + output.getLASTNAME(),
                            SOLUTION_TITLE, STATUS, SOLUTION_ID,
                            PRIMARY_KEY,
                            output.getWALLET_ID(),
                              JSON_SOLUTION_DATA,
                            token,  USERTYPE, PRICE, FILE_LIST);
                });

        // .switchIfEmpty(submissionError());


    }





}
