package com.projectroboticscontent.contentDatabaseAPI.Investor;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionSubmission;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class SubmitFinanceRequestService {

    private final MongoClient hardwareClient;
    private final ReactiveMongoTemplate hardwareMongoOps;

    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;

    private final MongoClient investorClient;
    private final ReactiveMongoTemplate investorMongoOps;

    private final MongoClient solutionClient;
    private final ReactiveMongoTemplate solutionMongoOps;

    public SubmitFinanceRequestService() {

        this.hardwareClient = MongoClients.create();
        this.hardwareMongoOps = new ReactiveMongoTemplate(hardwareClient, "testHardwareDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient,"testLoginDatabase");

        this.investorClient = MongoClients.create();
        this.investorMongoOps = new ReactiveMongoTemplate(investorClient,"testInvestorDatabase");

        this.solutionClient = MongoClients.create();
        this.solutionMongoOps = new ReactiveMongoTemplate(solutionClient, "testSolutionDatabase");
    }




    private Mono<String> newFinanceRequest(long STATUS, long FINANCE_REQUEST_ID, long TOTAL_COST,
                                   String SOLUTION_TITLE, long PAYMENT,
                                   long USAGE_TIME, long SOLUTION_PROVIDER_PRIMARY_KEY,
                                   long INVESTOR_PRIMARY_KEY,
                                   String SOLUTION_PROVIDER_WALLET_ID, String INVESTOR_WALLET_ID,
                                   String HARDWARE_ID, long USER_PRIMARY_KEY,
                                   String USER_WALLET_ID, long SOLUTION_ID,
                                   String SOLUTION_OWNER, String FINANCE_REQUEST_OWNER)
    {


        final String DATETIME = LocalDateTime.now().toString();

        final String url = Configuration.blockchainIPAddress + "/sol/set";

        final FinanceRequest submission = new FinanceRequest( STATUS,  FINANCE_REQUEST_ID,  TOTAL_COST,
         SOLUTION_TITLE,  PAYMENT,
         USAGE_TIME,  SOLUTION_PROVIDER_PRIMARY_KEY,
         INVESTOR_PRIMARY_KEY,  DATETIME, SOLUTION_PROVIDER_WALLET_ID,  INVESTOR_WALLET_ID, HARDWARE_ID,
                USER_PRIMARY_KEY, USER_WALLET_ID,
                SOLUTION_ID, SOLUTION_OWNER, FINANCE_REQUEST_OWNER);

        WebClient addBlockClient = WebClient.create();

        return investorMongoOps.insert(submission,"FinanceRequestCollection")
                .flatMap(
                        output->{
                            return addBlockClient
                                    .get()
                                    .uri(url+"?key=" + Long.toString(FINANCE_REQUEST_ID) + "&value="
                                            +output.getHASH())
                                    .retrieve()
                                    .bodyToMono(String.class);
                        }
                )
                .switchIfEmpty(Mono.just("error"));


    }

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


    private Mono<String> newSubmission( long TOTAL_COST,
                               long PAYMENT,
                               long USAGE_TIME,
                               String HARDWARE_ID, long SOLUTION_ID, String FINANCE_REQUEST_OWNER)
    {


        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)

                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return solutionMongoOps.findOne(query, SolutionSubmission.class,"SolutionProviderCollection")

                .flatMap(output ->{
                    return newFinanceRequest(0, System.currentTimeMillis(),  TOTAL_COST,
                            output.getSOLUTION_TITLE(),  PAYMENT, USAGE_TIME,
                            output.getSOLUTION_PROVIDER_PRIMARY_KEY(),
                    0,
                            output.getSOLUTION_PROVIDER_WALLET_ID(), "",
                            HARDWARE_ID, output.getUSER_PRIMARY_KEY(),
                            output.getUSER_WALLET_ID(), output.getSOLUTION_ID(),
                            output.getSOLUTION_OWNER(), FINANCE_REQUEST_OWNER);
                })
                .switchIfEmpty(Mono.just("error"));




    }

    public Mono<String> newSubmission2(String token, String USERTYPE,   long TOTAL_COST,
                               long PAYMENT,
                               long USAGE_TIME,
                               String HARDWARE_ID, long SOLUTION_ID)
    {

        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output->{
                    return newSubmission(TOTAL_COST,
                            PAYMENT,
                            USAGE_TIME,
                            HARDWARE_ID,  SOLUTION_ID, output.getFIRSTNAME()+" "+output.getLASTNAME());
                })
                .switchIfEmpty(Mono.just("error"));




    }




}
