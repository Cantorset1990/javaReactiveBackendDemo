package com.projectroboticscontent.contentDatabaseAPI.PrivateComments;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.PrivateComments.CommentStorage;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormDataRetrieval;
import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionDataRetrieval;
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
public class SolutionCommentsService {

   // private MongoClient client;
   // private ReactiveMongoTemplate MongoOps;

    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;

    private MongoClient solutionClient;
    private ReactiveMongoTemplate solutionMongoOps;

    private MongoClient userClient;
    private ReactiveMongoTemplate userMongoOps;

    public SolutionCommentsService() {

       // this.client = MongoClients.create();
       // this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");

        this.solutionClient = MongoClients.create();
        this.solutionMongoOps = new ReactiveMongoTemplate(solutionClient, "testSolutionDatabase");

        this.userClient = MongoClients.create();
        this.userMongoOps = new ReactiveMongoTemplate(solutionClient, "testUserDatabase");
    }

    private ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Submission Error\n");
    }



    private Mono submissionError(){

        return Mono.just(createStatusException());

    }

    private Mono Error(){

        return Mono.just(createStatusException());

    }

    private Mono updateMessage(){

        return Mono.just("Creation was successful");
    }


    private Mono submissionSuccess ()
    {
        return Mono.just("Submission was successful");
    }

    /*
    private Mono addCommentList(long[] COMMENTS, long COMMENT_ID)
    {
        long newarr[] = new long[COMMENTS.length + 1];

        for (int i = 0; i < COMMENTS.length; i++)
            newarr[i] = COMMENTS[i];

        newarr[COMMENTS.length] = COMMENT_ID;

        return Mono.just(newarr);
    }

    private Mono updateSolutionComments(long SOLUTION_ID, long[] COMMENTS)
    {
        //final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                                // Criteria.where("FORM_ID").is(formID)
                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");

        Update update = new Update();
        update.set("COMMENTS", COMMENTS);
        //update.set("FORM_TITLE", FORM_TITLE);

        return solutionMongoOps.findAndModify(query,update, SolutionSubmission.class,"SolutionProviderCollection")
                .flatMap(output->{return updateMessage();})
                .switchIfEmpty(Error());


    }



    private Mono getAndCommentSolutionList( long SOLUTION_ID, long COMMENT_ID )
    {


        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                // Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));





        return solutionMongoOps.findOne(query, SolutionSubmission.class,"SolutionProviderCollection")

                .flatMap(output ->{
                    return addCommentList(output.getCOMMENTS(), COMMENT_ID);
                })
                .flatMap(
                        output->{
                            return updateSolutionComments(SOLUTION_ID, (long[]) output);
                        }
                )

                .switchIfEmpty(Error());
    }


     */



    private Mono newComment(String token, String USERTYPE,
                            String  COMMENT_DATA,
                            long PRIMARY_KEY_SOLUTION,long COMMENT_ID, long SOLUTION_ID,
                            String COMMENT_OWNER, long PRIMARY_KEY_USER, long FORM_ID)
    {


        final CommentStorage Comment = new CommentStorage ( token, USERTYPE,  SOLUTION_ID, PRIMARY_KEY_SOLUTION,
                COMMENT_ID,  COMMENT_DATA,  COMMENT_OWNER, PRIMARY_KEY_USER, FORM_ID) ;


        return solutionMongoOps.insert(Comment,"SolutionCommentsCollection")
                .flatMap(output ->{
                    return submissionSuccess();
                });


    }

    private Mono newCommentSolutionProvider(String token, String USERTYPE,
                             String  COMMENT_DATA,
                             long PRIMARY_KEY_SOLUTION,long COMMENT_ID, long SOLUTION_ID,
                             String COMMENT_OWNER,  long FORM_ID)
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
                            return newComment(token,  USERTYPE,
                                     COMMENT_DATA,
                             PRIMARY_KEY_SOLUTION, COMMENT_ID,  SOLUTION_ID,
                             COMMENT_OWNER, output.getUSER_PRIMARY_KEY(), FORM_ID);

                        }
                );

    }

    private Mono newCommentUser(String token, String USERTYPE,
                                String  COMMENT_DATA,
                                long PRIMARY_KEY_USER,long COMMENT_ID, long SOLUTION_ID,
                                String COMMENT_OWNER,  long FORM_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(

                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));

        // query.fields().include("PRIMARY_KEY");


        return solutionMongoOps.findOne(query, SolutionDataRetrieval.class,"SolutionProviderCollection")
                .flatMap(
                        output->{
                            return newComment(token,  USERTYPE,
                                    COMMENT_DATA,
                                    output.getSOLUTION_PROVIDER_PRIMARY_KEY(), COMMENT_ID,  SOLUTION_ID,
                                    COMMENT_OWNER, PRIMARY_KEY_USER, FORM_ID);

                        }
                );

    }



    private Mono newSubmissionSolutionProvider(String token, String USERTYPE,
                              String COMMENT_DATA,
                              long SOLUTION_ID, long FORM_ID)
    {
        final long PRIMARY_KEY_SOLUTION = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long COMMENT_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY_SOLUTION)

                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output ->{
                    return newCommentSolutionProvider( token,  USERTYPE,
                            COMMENT_DATA,
                            PRIMARY_KEY_SOLUTION, COMMENT_ID,  SOLUTION_ID, output.getFIRSTNAME() + " " + output.getLASTNAME()
                    , FORM_ID);
                })

                .switchIfEmpty(submissionError());


    }



    private Mono newSubmissionUser(String token, String USERTYPE,
                                              String COMMENT_DATA,
                                              long SOLUTION_ID, long FORM_ID)
    {
        final long PRIMARY_KEY_USER = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long COMMENT_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY_USER)

                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output ->{
                    return newCommentUser( token, USERTYPE,
                              COMMENT_DATA,
                     PRIMARY_KEY_USER, COMMENT_ID,  SOLUTION_ID,
                            output.getFIRSTNAME() + " " + output.getLASTNAME(),  FORM_ID);
                })

                .switchIfEmpty(submissionError());


    }


    public Mono newFinalSubmissionSolutionProvider(String token, String USERTYPE,
                                              String COMMENT_DATA,
                                              long SOLUTION_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(

                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));

        return solutionMongoOps.findOne(query, SolutionDataRetrieval.class,"SolutionProviderCollection")
                .flatMap(output->{
                    return newSubmissionSolutionProvider( token,  USERTYPE,
                             COMMENT_DATA,
                     SOLUTION_ID, output.getFORM_ID());
                });
    }

    public Mono newFinalSubmissionUser(String token, String USERTYPE,
                                  String COMMENT_DATA,
                                  long SOLUTION_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(

                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));

        return solutionMongoOps.findOne(query, SolutionDataRetrieval.class,"SolutionProviderCollection")
                .flatMap(output->{
                   return newSubmissionUser(token, USERTYPE,
                            COMMENT_DATA,
                     SOLUTION_ID, output.getFORM_ID());
                });
    }

    public Flux getAllSolutionComments(String token, String USERTYPE, long SOLUTION_ID ){

        final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().orOperator(
                             //   Criteria.where("PRIMARY_KEY_USER").is(ID),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));

        return solutionMongoOps.find(query, CommentStorage.class,"SolutionCommentsCollection")
                .switchIfEmpty(Error());


    }

}
