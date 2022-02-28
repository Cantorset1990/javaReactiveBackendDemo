package com.projectroboticscontent.contentDatabaseAPI.Investor;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
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
public class UpdateFinanceService {

    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;

    private MongoClient solutionClient;
    private ReactiveMongoTemplate solutionMongoOps;

    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;


    public UpdateFinanceService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testInvestorDatabase");

        this.solutionClient = MongoClients.create();
        this.solutionMongoOps = new ReactiveMongoTemplate(solutionClient, "testSolutionDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }

    private ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error\n");
    }

    private Mono createStatusException2(long id){
        return
                Mono.just(String.valueOf(id));
    }

    public Mono Error(){

        return Mono.just(createStatusException());

    }

    public Mono updateMessage(){

        return Mono.just("Update was successful");
    }

    private Mono checkProfile(String token, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .switchIfEmpty(Error());


    }

    public Mono updateFinanceStatus(String token, long FINANCE_REQUEST_ID, long STATUS,  String USERTYPE)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);
        // final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("INVESTOR_PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("FINANCE_REQUEST_ID").is(FINANCE_REQUEST_ID)
                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");

        Update update = new Update();
        update.set("STATUS", STATUS);
        //update.set("FORM_TITLE", FORM_TITLE);

        return MongoOps.findAndModify(query,update, FinanceSubmission.class,"FinanceRequestCollection")
                .flatMap(output->{return updateMessage();})
                .switchIfEmpty(Error());


    }

}
