package com.projectroboticscontent.contentDatabaseAPI.PublicFiles;

import com.google.common.hash.Hashing;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;

import com.projectroboticscontent.contentDatabaseAPI.Utility.File;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Service
public class PublicFileService {

    private final MongoClient client;
    private final ReactiveMongoTemplate MongoOps;

    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;

    public PublicFileService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");

    }

    private ResponseStatusException createStatusException() {
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Submission Error\n");
    }

    private Mono<UserProfile> checkProfile(String token, String USERTYPE) {
        final long ID = JwtTokenUtil.parseJWTProfile(token);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));


        return registrationMongoOps.findOne(query, UserProfile.class, "RegistrationCollection");
            //    .switchIfEmpty(submissionError());


    }

    private Mono<Exception> submissionError() {

        return Mono.just(createStatusException());

    }

    private Mono<File> newFile(String token, String USERTYPE, long FORM_ID, String FILE_OWNER, long FILE_ID, long PRIMARY_KEY,
                         String FILE_NAME, String FILE_TYPE, long FILE_SIZE, String FILE_INDEX, String BINARY_FILE_DATA) {


        final PublicFileStorage File = new PublicFileStorage(token, USERTYPE, FORM_ID, FILE_OWNER, FILE_ID, PRIMARY_KEY,
                FILE_NAME, FILE_TYPE, FILE_SIZE, FILE_INDEX, Hashing.sha256()
                .hashString(BINARY_FILE_DATA, StandardCharsets.UTF_8)
                .toString());

        final File Data = new File(BINARY_FILE_DATA, FILE_ID);


        return MongoOps.insert(File, "UserFileCollection")
                .flatMap(output ->{
                    return MongoOps.insert(Data, "UserFileCollection");
                });

        //int x = MongoOps.insert(Data, "UserFileCollection");
    }




    public Mono<File> newSubmission(String token, String USERTYPE, long FORM_ID,
                              String FILE_NAME, String FILE_TYPE, long FILE_SIZE, String FILE_INDEX, String BINARY_FILE_DATA)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long FILE_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));




        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output ->{
                    return newFile( token,  USERTYPE,  FORM_ID, output.getFIRSTNAME()+" "+output.getLASTNAME(),
                            FILE_ID, PRIMARY_KEY,
                     FILE_NAME,  FILE_TYPE,  FILE_SIZE,  FILE_INDEX,  BINARY_FILE_DATA);
                });




    }

    private Flux<PublicFileInfo> getAllUserFiles(long FORM_ID){



        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                //Criteria.where("PRIMARY_KEY").is(ID),
                                Criteria.where("FORM_ID").is(FORM_ID )
                        ));

        return MongoOps.find(query, PublicFileInfo.class,"UserFileCollection");


    }


    private Mono<File> getOneUserFile(long DATA_ID){



        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                //Criteria.where("PRIMARY_KEY").is(ID),
                                Criteria.where("DATA_ID").is(DATA_ID )
                        ));



        return MongoOps.findOne(query, File.class,"UserFileCollection");


    }

    public  Flux<PublicFileInfo> viewAllUserFiles(String token, String USERTYPE, long FORM_ID)
    {
       // final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);


        return checkProfile(token, USERTYPE)
                .flatMapMany(output->{
                    return getAllUserFiles( FORM_ID);
                });



        // return viewAllUserForms(ID);
    }

    public  Mono<File> viewOneUserFile(String token, String USERTYPE, long FILE_ID)
    {
        // final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);


        return checkProfile(token, USERTYPE)
                .flatMap(output->{
                    return getOneUserFile(FILE_ID);
                });



        // return viewAllUserForms(ID);
    }


}
