package com.projectroboticscontent.contentDatabaseAPI.PublicComments;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
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
public class UserCommentsService {

    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;

    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;

    public UserCommentsService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");

    }

    private Mono submissionSuccess ()
    {
        return Mono.just("Submission was successful");
    }

    private ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Submission Error\n");
    }

    private Mono checkProfile(String token, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWTProfile(token);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .switchIfEmpty(submissionError());


    }

    private Mono submissionError(){

        return Mono.just(createStatusException());

    }

    private Mono newComment(String token, String USERTYPE,
                            String  COMMENT_DATA,
                            long PRIMARY_KEY,long COMMENT_ID, long FORM_ID, String COMMENT_OWNER)
    {


        final UserCommentStorage Comment = new UserCommentStorage(token, USERTYPE,  PRIMARY_KEY,  COMMENT_ID,
         COMMENT_DATA, COMMENT_OWNER,  FORM_ID);


        return MongoOps.insert(Comment,"UserCommentsCollection");



    }

    public Mono newSubmission(String token, String USERTYPE,
                              String COMMENT_DATA,
                              long FORM_ID)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long COMMENT_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output ->{
                    return newComment( token,  USERTYPE,
                            COMMENT_DATA,
                            PRIMARY_KEY, COMMENT_ID,  FORM_ID, output.getFIRSTNAME() + " " + output.getLASTNAME());
                })
                .flatMap(output ->{
                    return submissionSuccess();
                })

                .switchIfEmpty(submissionError());


    }

    private Flux getAllSolutionComments(long FORM_ID){

       // final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                //Criteria.where("PRIMARY_KEY").is(ID),
                                Criteria.where("FORM_ID").is(FORM_ID )
                        ));

        return MongoOps.find(query, UserCommentStorage.class,"UserCommentsCollection");


    }

    public  Flux viewAllUserFormsByProvider(String token, String USERTYPE, long FORM_ID)
    {
       // final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);


        return checkProfile(token, USERTYPE)
                .flatMapMany(output->{
                    return getAllSolutionComments(FORM_ID);
                });



        // return viewAllUserForms(ID);
    }





}
