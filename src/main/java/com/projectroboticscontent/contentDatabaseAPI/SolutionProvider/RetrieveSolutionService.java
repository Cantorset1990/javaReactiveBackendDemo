package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormDataRetrieval;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormInfo;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormInput;
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
public class RetrieveSolutionService {


    private MongoClient solutionClient;
    private ReactiveMongoTemplate solutionMongoOps;

    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;

    public RetrieveSolutionService() {

        this.solutionClient = MongoClients.create();
        this.solutionMongoOps = new ReactiveMongoTemplate(solutionClient, "testSolutionDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }

    private ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error\n");
    }

    private Mono Error(){

       // return Mono.just(createStatusException());
        return Mono.just("No forms found");

    }

    public Mono formNotFoundMessage(){

        return Mono.just("Form not found");

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

    public Flux getAllSolutionsForOnePersonStatus(String token, String USERTYPE, long STATUS)
    {
        final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(ID),
                                Criteria.where("STATUS").is(STATUS)
                        ));

        // query.fields().include("FORM_ID");
        // query.fields().include("JSON_FORM_DATA");
        // query.fields().include("FORM_TITLE");

        return solutionMongoOps.find(query, SolutionDataRetrieval.class,"SolutionProviderCollection")
                .switchIfEmpty(Error());


    }


    public Flux getAllSolutionsForOnePerson(String token, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(ID)

                        ));

        // query.fields().include("FORM_ID");
        // query.fields().include("JSON_FORM_DATA");
        // query.fields().include("FORM_TITLE");

        return solutionMongoOps.find(query, SolutionDataRetrieval.class,"SolutionProviderCollection")
                .switchIfEmpty(Error());


    }

    private Flux getSolutionsForOneForm(long FORM_ID)
    {
        //final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                //Criteria.where("PRIMARY_KEY").is(ID),
                                Criteria.where("FORM_ID").is(FORM_ID)
                        ));

        //  query.fields().include("FORM_ID");
        //  query.fields().include("JSON_FORM_DATA");
        //  query.fields().include("FORM_TITLE");

        return solutionMongoOps.find(query, SolutionDataRetrieval.class,"SolutionProviderCollection")
                .switchIfEmpty(Error());


    }

    private Mono getOneSolutionForOneForm(long FORM_ID, long SOLUTION_ID)
    {
        //final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(

                                Criteria.where("FORM_ID").is(FORM_ID),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));

        //  query.fields().include("FORM_ID");
        //  query.fields().include("JSON_FORM_DATA");
        //  query.fields().include("FORM_TITLE");

        return solutionMongoOps.findOne(query, SolutionDataRetrieval.class,"SolutionProviderCollection")
                .switchIfEmpty(Error());


    }

    public Mono getAndCheckSolutionForOneForm(long FORM_ID, long SOLUTION_ID, String token, String USERTYPE)
    {

        return checkProfile(token, USERTYPE)
                .flatMap(output ->{
                    return getOneSolutionForOneForm( FORM_ID,  SOLUTION_ID);
                });


    }

    public Flux getAndCheckSolutionForOneForm(long FORM_ID,String token, String USERTYPE)
    {

        return checkProfile(token, USERTYPE)
                .flatMapMany(output ->{
                    return getSolutionsForOneForm(FORM_ID);
                });


    }

    private Mono getOneSolution(long SOLUTION_ID)
    {
        //final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                //Criteria.where("PRIMARY_KEY").is(ID),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));

        //  query.fields().include("FORM_ID");
        //  query.fields().include("JSON_FORM_DATA");
        //  query.fields().include("FORM_TITLE");

        return solutionMongoOps.findOne(query, SolutionDataRetrieval.class,"SolutionProviderCollection")
                .switchIfEmpty(Error());


    }



    public Mono getAndCheckSolution(long SOLUTION_ID,String token, String USERTYPE)
    {

        return checkProfile(token, USERTYPE)
                .flatMap(output ->{
                    return getOneSolution(SOLUTION_ID);
                });



    }


    public Flux getSubmittedSolutionsForOneForm(String token, String USERTYPE, long FORM_ID)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(

                                Criteria.where("FORM_ID").is(FORM_ID),
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(PRIMARY_KEY)
                        ));

        //  query.fields().include("FORM_ID");
        //  query.fields().include("JSON_FORM_DATA");
        //  query.fields().include("FORM_TITLE");

        return solutionMongoOps.find(query, SolutionDataRetrieval.class,"SolutionProviderCollection")
                .switchIfEmpty(Error());


    }

    public Mono getSubmittedSolutionForOneForm(String token, String USERTYPE, long FORM_ID, long SOLUTION_ID)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID),
                                Criteria.where("FORM_ID").is(FORM_ID),
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(PRIMARY_KEY)
                        ));

        //  query.fields().include("FORM_ID");
        //  query.fields().include("JSON_FORM_DATA");
        //  query.fields().include("FORM_TITLE");

        return solutionMongoOps.findOne(query, SolutionDataRetrieval.class,"SolutionProviderCollection")
                .switchIfEmpty(Error());


    }



}
