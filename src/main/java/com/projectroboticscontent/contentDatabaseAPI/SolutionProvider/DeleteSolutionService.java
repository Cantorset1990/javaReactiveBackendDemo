package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
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
public class DeleteSolutionService {

    private MongoClient solutionClient;
    private ReactiveMongoTemplate solutionMongoOps;

    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;

    public  DeleteSolutionService() {

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

        return Mono.just("Delete was successful");
    }

    public Mono removeSolution(long[] SOLUTION_LIST, long SOLUTION_ID)
    {
        // SolutionInfo newarr[] = new SolutionInfo[SOLUTION_LIST.length];

        for (int i = 0; i < SOLUTION_LIST.length; i++) {
            if (SOLUTION_LIST[i] == SOLUTION_ID) {
                SOLUTION_LIST[i] = 0;
            }
        }

        // newarr[FORM_PERMISSION.length] = FORM_ID;

        //return Mono.just(newarr);

        return Mono.just(SOLUTION_LIST);
    }




    public Mono getAndDeleteSolutionList( long FORM_ID,
                                         long SOLUTION_ID )
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
                    return removeSolution(output.getSOLUTION_LIST(), SOLUTION_ID);
                })
                .flatMap(
                        output->{
                            return updateSolutionList(FORM_ID, (long[]) output);
                        }
                )

                .switchIfEmpty(Error());
    }



    public Mono updateSolutionList(long FORM_ID, long[] SOLUTION_LIST)
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




    public Mono deleteSolution(String token, long SOLUTION_ID, long FORM_ID,
                                String USERTYPE)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");

       // Update update = new Update();
      //  update.set("JSON_FORM_DATA", JSON_SOLUTION_DATA);
       // update.set("FORM_TITLE", SOLUTION_TITLE);

        return solutionMongoOps.findAndRemove(query,SolutionSubmission.class,"SolutionProviderCollection")
                .flatMap(output->{
                    return getAndDeleteSolutionList( FORM_ID,
                             SOLUTION_ID );
                })
                .flatMap(output->{return updateMessage();})
                .switchIfEmpty(Error());


    }


}
