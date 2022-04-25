package com.projectroboticscontent.contentDatabaseAPI.Market;

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
public class SolutionMarketViewService {

    private final MongoClient client;
    private final ReactiveMongoTemplate MongoOps;

    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;

    public SolutionMarketViewService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testSolutionDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Error\n");
    }

    public Mono<String> Error(){

        //  return Mono.just(createStatusException());

        return Mono.just("No Solution found");

    }

    public Mono<String> solutionNotFoundMessage(){

        return Mono.just("Solution not found");

    }

    public Flux<SolutionMarketSubmission> getAllMarketSolutions(String token, String USERTYPE)
    {


        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(PRIMARY_KEY)

                        ));


        return MongoOps.find(query, SolutionMarketSubmission.class,"SolutionProviderCollection")
                .switchIfEmpty(Mono.just(new SolutionMarketSubmission("Error, check token and json input", HttpStatus.INTERNAL_SERVER_ERROR,
                        "", "", 404, 0, 0,"",
                        null, "", "", "", 0)));
        //.switchIfEmpty(Error());




    }


    public Flux<SolutionMarketSubmission> getAllMarketSolutionStatus(String token, String USERTYPE, long STATUS)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("STATUS").is(STATUS)

                        ));



        return MongoOps.find(query, SolutionMarketSubmission.class,"SolutionProviderCollection")
                .switchIfEmpty(Mono.just(new SolutionMarketSubmission("Error, check token and json input", HttpStatus.INTERNAL_SERVER_ERROR,
                        "", "", 404, 0, 0,"",
                        null, "", "", "", 0)));





    }

    public Mono<SolutionMarketSubmission> getOneMarketSolution(String token, long SOLUTION_ID, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(ID),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));



        return MongoOps.findOne(query, SolutionMarketSubmission.class,"SolutionProviderCollection")
                .switchIfEmpty(Mono.just(new SolutionMarketSubmission("Error, check token and json input", HttpStatus.INTERNAL_SERVER_ERROR,
                        "", "", 404, 0, 0,"",
                        null, "", "", "", 0)));
        //.switchIfEmpty(Error());



    }

    ////////////////////////////////////////////////////////////////////////////

    private Mono<UserProfile> checkProfile(String token, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));



        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection");



    }

    private Flux<SolutionMarketSubmission> viewAllMarketSolutions()
    {


        return MongoOps.findAll(SolutionMarketSubmission.class,"SolutionProviderCollection")
                .switchIfEmpty(Mono.just(new SolutionMarketSubmission("Error, check token and json input", HttpStatus.INTERNAL_SERVER_ERROR,
                        "", "", 404, 0, 0,"",
                        null, "", "", "", 0)));


    }

    public Flux<SolutionMarketSubmission> viewAllMarketSolutionStatus(String token, String USERTYPE, long STATUS)
    {


        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("STATUS").is(STATUS)

                        ));



        return checkProfile(token, USERTYPE)
                .flatMapMany(output->{

                    return MongoOps.find(query, SolutionMarketSubmission.class,
                            "SolutionProviderCollection");
                    //.switchIfEmpty(Error());

                })
                .switchIfEmpty(Mono.just(new SolutionMarketSubmission("Error, check token and json input", HttpStatus.INTERNAL_SERVER_ERROR,
                        "", "", 404, 0, 0,"",
                        null, "", "", "", 0)));




    }

    private Mono<SolutionMarketSubmission> viewSingleMarketSolution(long SOLUTION_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(

                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));

        return MongoOps.findOne(query,SolutionMarketSubmission.class,
                "SolutionProviderCollection")
                .switchIfEmpty(Mono.just(new SolutionMarketSubmission("Error, check token and json input", HttpStatus.INTERNAL_SERVER_ERROR,
                        "", "", 404, 0, 0,"",
                        null, "", "", "", 0)));
        //.switchIfEmpty(Error());
    }

    public  Flux<SolutionMarketSubmission> viewAllMarketSolutionsByProvider(String token, String USERTYPE)
    {
        // final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);


        return checkProfile(token, USERTYPE)
                .flatMapMany(output->{
                    return viewAllMarketSolutions();
                })
                .switchIfEmpty(Mono.just(new SolutionMarketSubmission("Error, check token and json input", HttpStatus.INTERNAL_SERVER_ERROR,
                        "", "", 404, 0, 0,"",
                        null, "", "", "", 0)));



        // return viewAllUserForms(ID);
    }

    public  Mono<SolutionMarketSubmission> viewSingleFormByProvider(String token, String USERTYPE, long SOLUTION_ID)
    {
        //  final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);


        return checkProfile(token, USERTYPE)
                .flatMap(output->{
                    return viewSingleMarketSolution(SOLUTION_ID);
                })
                .switchIfEmpty(Mono.just(new SolutionMarketSubmission("Error, check token and json input", HttpStatus.INTERNAL_SERVER_ERROR,
                        "", "", 404, 0, 0,"",
                        null, "", "", "", 0)));




    }



}
