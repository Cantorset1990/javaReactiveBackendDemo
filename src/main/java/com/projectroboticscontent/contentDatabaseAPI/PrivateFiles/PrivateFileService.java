package com.projectroboticscontent.contentDatabaseAPI.PrivateFiles;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;


import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionInfo;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PrivateFileService {


    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;

    private final MongoClient solutionClient;
    private final ReactiveMongoTemplate solutionMongoOps;

    private final MongoClient userClient;
    private final ReactiveMongoTemplate userMongoOps;

    public PrivateFileService() {

        // this.client = MongoClients.create();
        // this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");

        this.solutionClient = MongoClients.create();
        this.solutionMongoOps = new ReactiveMongoTemplate(solutionClient, "testSolutionDatabase");

        this.userClient = MongoClients.create();
        this.userMongoOps = new ReactiveMongoTemplate(userClient, "testUserDatabase");
    }

    private ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Submission Error\n");
    }

    private Mono<Exception> submissionError(){

        return Mono.just(createStatusException());

    }

    private Mono<Exception> Error(){

        return Mono.just(createStatusException());

    }

    private Mono<String> updateMessage(){

        return Mono.just("Creation was successful");
    }


    private Mono<String> submissionSuccess ()
    {
        return Mono.just("Submission was successful");
    }




    private Mono<String> newFileStorage(String token, String USERTYPE, long SOLUTION_ID, long FORM_ID,
                                String FILE_OWNER, long SOLUTION_PROVIDER_PRIMARY_KEY, long FILE_ID,
                                long USER_PRIMARY_KEY, String FILE_NAME, String FILE_TYPE,
                                long FILE_SIZE, String FILE_INDEX, String BINARY_FILE_DATA)
    {


        final PrivateFileStorage File = new PrivateFileStorage( token,  USERTYPE,  SOLUTION_ID,  FORM_ID,
         FILE_OWNER,  SOLUTION_PROVIDER_PRIMARY_KEY, FILE_ID,
                USER_PRIMARY_KEY, FILE_NAME, FILE_TYPE,
         FILE_SIZE,  FILE_INDEX,  BINARY_FILE_DATA) ;


        return solutionMongoOps.insert(File,"SolutionFileCollection")
                .flatMap(output ->{
                    return submissionSuccess();
                })
                .switchIfEmpty(Mono.just("error"));


    }
    /*
    private Mono newFileSolutionProvider(String token, String USERTYPE, long SOLUTION_ID, long FORM_ID,
                                         String FILE_OWNER, long SOLUTION_PROVIDER_PRIMARY_KEY, long FILE_ID,
                                         String FILE_NAME, String FILE_TYPE,
                                         long FILE_SIZE, String FILE_INDEX, Binary BINARY_FILE_DATA)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                // Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("FORM_ID").is(FORM_ID)
                        ));

        // query.fields().include("PRIMARY_KEY");


        return userMongoOps.findOne(query, FormDataRetrieval.class,"UserRequestCollection")
                .flatMap(
                        output->{
                            return newFileStorage( token,  USERTYPE,  SOLUTION_ID,  FORM_ID,
                             FILE_OWNER,  SOLUTION_PROVIDER_PRIMARY_KEY, FILE_ID,
                                    output.getUSER_PRIMARY_KEY(),  FILE_NAME,  FILE_TYPE,
                            FILE_SIZE,  FILE_INDEX,  BINARY_FILE_DATA);

                        }
                );

    }

    */

    public Mono<String> newSolutionProviderFileSubmission(String token, String USERTYPE, long SOLUTION_ID,
                             String FILE_NAME, String FILE_TYPE,
                             long FILE_SIZE, String FILE_INDEX, String BINARY_FILE_DATA)
    {

        final long SOLUTION_PROVIDER_PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long FILE_ID = System.currentTimeMillis();



        final Query query =
                Query.query(
                        new Criteria().andOperator(

                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID),
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(SOLUTION_PROVIDER_PRIMARY_KEY)
                        ));

        // query.fields().include("PRIMARY_KEY");




        return solutionMongoOps.findOne(query, SolutionInfo.class,"SolutionProviderCollection")
                .flatMap(
                        output->{

                            return newFileStorage( token,  USERTYPE,  SOLUTION_ID, output.getFORM_ID(),
                                    output.getSOLUTION_OWNER(), output.getSOLUTION_PROVIDER_PRIMARY_KEY(), FILE_ID,
                                    output.getUSER_PRIMARY_KEY(),  FILE_NAME,  FILE_TYPE,
                                    FILE_SIZE,  FILE_INDEX,  BINARY_FILE_DATA);
                        }
                )
                .switchIfEmpty(Mono.just("error"));

    }

    public Mono<String> newUserFileSubmission(String token, String USERTYPE, long SOLUTION_ID,
                                                   String FILE_NAME, String FILE_TYPE,
                                                   long FILE_SIZE, String FILE_INDEX, String BINARY_FILE_DATA)
    {

        final long USER_PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long FILE_ID = System.currentTimeMillis();


        final Query query =
                Query.query(
                        new Criteria().andOperator(

                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID),
                                Criteria.where("USER_PRIMARY_KEY").is(USER_PRIMARY_KEY)
                        ));

        // query.fields().include("PRIMARY_KEY");




        return solutionMongoOps.findOne(query, SolutionInfo.class,"SolutionProviderCollection")
                .flatMap(
                        output->{

                            return newFileStorage( token,  USERTYPE,  SOLUTION_ID, output.getFORM_ID(),
                                    output.getSOLUTION_OWNER(), output.getSOLUTION_PROVIDER_PRIMARY_KEY(), FILE_ID,
                                    output.getUSER_PRIMARY_KEY(),  FILE_NAME,  FILE_TYPE,
                                    FILE_SIZE,  FILE_INDEX,  BINARY_FILE_DATA);
                        }
                )
                .switchIfEmpty(Mono.just("error"));

    }


    public Flux<PrivateFileInfo> getAllFilesUser(String token, String USERTYPE, long SOLUTION_ID ){

        final long USER_PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("USER_PRIMARY_KEY").is(USER_PRIMARY_KEY),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID )
                        )
                );


        return solutionMongoOps.find(query, PrivateFileInfo.class,"SolutionFileCollection");



    }




    public Flux<PrivateFileInfo> getAllFilesBySolutionProvider(String token, String USERTYPE, long SOLUTION_ID ) {

        final long SOLUTION_PROVIDER_PRIMARY_KEY = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(SOLUTION_PROVIDER_PRIMARY_KEY),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        )
                );


        return solutionMongoOps.find(query, PrivateFileInfo.class, "SolutionFileCollection");


    }

    public Mono<PrivateFileStorage> getOneFilesUser(String token, String USERTYPE, long FILE_ID ){

        final long USER_PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("USER_PRIMARY_KEY").is(USER_PRIMARY_KEY),
                                Criteria.where("FILE_ID").is(FILE_ID )
                        )
                );


        return solutionMongoOps.findOne(query, PrivateFileStorage.class,"SolutionFileCollection");



    }




    public Mono<PrivateFileStorage> getOneFileBySolutionProvider(String token, String USERTYPE, long FILE_ID ) {

        final long SOLUTION_PROVIDER_PRIMARY_KEY = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(SOLUTION_PROVIDER_PRIMARY_KEY),
                                Criteria.where("FILE_ID").is(FILE_ID)
                        )
                );


        return solutionMongoOps.findOne(query, PrivateFileStorage.class, "SolutionFileCollection");


    }






}
