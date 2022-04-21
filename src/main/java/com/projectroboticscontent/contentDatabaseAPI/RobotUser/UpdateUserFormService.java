package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionInput;
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
public class UpdateUserFormService {

    private final MongoClient client;
    private final ReactiveMongoTemplate MongoOps;

    private final MongoClient solutionClient;
    private final ReactiveMongoTemplate solutionMongoOps;

    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;


    public UpdateUserFormService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");

        this.solutionClient = MongoClients.create();
        this.solutionMongoOps = new ReactiveMongoTemplate(solutionClient, "testSolutionDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error\n");
    }

    public Mono<String> createStatusException2(long id){
        return
                Mono.just(String.valueOf(id));
    }

    public Mono<ResponseStatusException> Error(){

        return Mono.just(createStatusException());

    }

    public Mono<String> updateMessage(){

        return Mono.just("Update was successful");
    }

    public Mono<UserProfile> checkProfile(String token, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection");
                //.switchIfEmpty(Error());


    }

    public Mono<String> updateForm(String token, long FORM_ID, String JSON_FORM_DATA, String FORM_TITLE, String USERTYPE)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);
       // final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("USER_PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("FORM_ID").is(FORM_ID)
                        ));



        Update update = new Update();
        update.set("JSON_FORM_DATA", JSON_FORM_DATA);
        update.set("FORM_TITLE", FORM_TITLE);

        return MongoOps.findAndModify(query,update, FormInput.class,"UserRequestCollection")
                .flatMap(output->{return updateMessage();});



    }

    public Mono<String> updateFormStatus(String token, long FORM_ID, String USERTYPE, long STATUS)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);
        // final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("USER_PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("FORM_ID").is(FORM_ID)
                        ));



        Update update = new Update();
        update.set("STATUS", STATUS);


        return MongoOps.findAndModify(query,update, FormInput.class,"UserRequestCollection")
                .flatMap(output->{return updateMessage();});
                //.switchIfEmpty(Error());


    }

    private Mono<SolutionInput> updateSolutionStatus1(long SOLUTION_ID, long STATUS)
    {


        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)

                        ));

        Update update = new Update();
        update.set("STATUS", STATUS);

        return solutionMongoOps.findAndModify(query, update, SolutionInput.class,"SolutionProviderCollection");


    }

    public Mono<SolutionInput> updateSolutionStatus2(String token, String USERTYPE, long SOLUTION_ID, long STATUS)
    {


        return checkProfile(token, USERTYPE)
                .flatMap(output->{
                    return updateSolutionStatus1(SOLUTION_ID, STATUS);
                });




    }
}
