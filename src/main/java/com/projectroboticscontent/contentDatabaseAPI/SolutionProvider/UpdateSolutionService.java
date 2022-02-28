package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormInfo;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class UpdateSolutionService {

    private MongoClient solutionClient;
    private ReactiveMongoTemplate solutionMongoOps;

    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;

    public  UpdateSolutionService() {

        this.solutionClient = MongoClients.create();
        this.solutionMongoOps = new ReactiveMongoTemplate(solutionClient, "testSolutionDatabase");

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");
    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error\n");
    }

    public Mono createStatusException2(long id){
        return
                Mono.just(String.valueOf(id));
    }

    public Mono Error(){

        return Mono.just(createStatusException());

    }

    public Mono updateMessage(){

        return Mono.just("Update was successful");
    }



    /*
    private Mono modifySolution(SolutionInfo[] SOLUTION_LIST, long SOLUTION_ID,
                               String SOLUTION_TITLE)
    {
        // SolutionInfo newarr[] = new SolutionInfo[SOLUTION_LIST.length];

        for (int i = 0; i < SOLUTION_LIST.length; i++) {
            if (SOLUTION_LIST[i].getSOLUTION_ID() == SOLUTION_ID) {
                SOLUTION_LIST[i].setSOLUTION_TITLE(SOLUTION_TITLE);
            }
        }

        // newarr[FORM_PERMISSION.length] = FORM_ID;

        //return Mono.just(newarr);

        return Mono.just(SOLUTION_LIST);
    }

     */

    /*
    private Mono getAndModifySolutionList( long FORM_ID,
                                      String SOLUTION_TITLE, long SOLUTION_ID )
    {
        //final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);


        //final long SOLUTION_ID = System.currentTimeMillis();

        //final SolutionInfo info = new SolutionInfo( SOLUTION_OWNER, SOLUTION_TITLE,
              //  SOLUTION_ID, FORM_ID,  PRIMARY_KEY);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                               // Criteria.where("SOLUTION_ID").is(SOLUTION_ID),
                                Criteria.where("FORM_ID").is(FORM_ID)
                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");




        return MongoOps.findOne(query, FormSubmission.class,"UserCollection")

                .flatMap(output ->{
                    return modifySolution(output.getSOLUTION_LIST(), SOLUTION_ID, SOLUTION_TITLE);
                })
                .flatMap(
                        output->{
                            return updateSolutionList(FORM_ID, (SolutionInfo[]) output);
                        }
                )

                .switchIfEmpty(Error());
    }



     */

    /*
    private Mono updateSolutionList(long FORM_ID, SolutionInfo[] SOLUTION_LIST)
    {
        //final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("FORM_ID").is(FORM_ID)
                                // Criteria.where("FORM_ID").is(formID)
                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");

        Update update = new Update();
        update.set("SOLUTION_LIST", SOLUTION_LIST);
        //update.set("FORM_TITLE", FORM_TITLE);

        return MongoOps.findAndModify(query,update, FormSubmission.class,"UserCollection")
                .flatMap(output->{return updateMessage();})
                .switchIfEmpty(Error());


    }

     */




    public Mono updateSolution(String token, long SOLUTION_ID,
                               String JSON_SOLUTION_DATA, String SOLUTION_TITLE, String USERTYPE)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");

        Update update = new Update();
        update.set("JSON_FORM_DATA", JSON_SOLUTION_DATA);
        update.set("FORM_TITLE", SOLUTION_TITLE);

        return solutionMongoOps.findAndModify(query,update, SolutionSubmission.class,"SolutionProviderCollection")
                .flatMap(output->{return updateMessage();})
                .switchIfEmpty(Error());


    }

    public Mono updateSolutionStatus(String token, long SOLUTION_ID,
                                String USERTYPE, long STATUS)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");

        Update update = new Update();
        update.set("STATUS", STATUS);


        return solutionMongoOps.findAndModify(query,update, SolutionSubmission.class,"SolutionProviderCollection")
                .flatMap(output->{return updateMessage();})
                .switchIfEmpty(Error());


    }

    public Mono updateSolutionStatusByUser(String token, long SOLUTION_ID,
                                     String USERTYPE, long STATUS)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("USER_PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");

        Update update = new Update();
        update.set("STATUS", STATUS);


        return solutionMongoOps.findAndModify(query,update, SolutionSubmission.class,"SolutionProviderCollection")
                .flatMap(output->{return updateMessage();})
                .switchIfEmpty(Error());


    }







}
