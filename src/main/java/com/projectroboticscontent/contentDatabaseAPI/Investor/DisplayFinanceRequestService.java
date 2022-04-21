package com.projectroboticscontent.contentDatabaseAPI.Investor;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DisplayFinanceRequestService {

    private final MongoClient hardwareClient;
    private final ReactiveMongoTemplate hardwareMongoOps;

    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;

    private final MongoClient investorClient;
    private final ReactiveMongoTemplate investorMongoOps;

    public  DisplayFinanceRequestService() {

        this.hardwareClient = MongoClients.create();
        this.hardwareMongoOps = new ReactiveMongoTemplate(hardwareClient, "testHardwareDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient,"testLoginDatabase");

        this.investorClient = MongoClients.create();
        this.investorMongoOps = new ReactiveMongoTemplate(investorClient,"testInvestorDatabase");
    }

    public Mono<UserProfile> Profile(String token, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));



        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection");

    }



    public Flux<FinanceRequest> RetrieveAllRequestStatus(String token, String USERTYPE, long STATUS)
    {

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("STATUS").is(STATUS)

                        ));

        return Profile(token, USERTYPE).flatMapMany(
                output -> {
                    return investorMongoOps.find(query, FinanceRequest.class, "FinanceRequestCollection");
                }
        );



    }

    public Flux<FinanceRequest> RetrieveAllRequest(String token, String USERTYPE)
    {



        return Profile(token, USERTYPE).flatMapMany(
                output -> {
                    return investorMongoOps.findAll( FinanceRequest.class, "FinanceRequestCollection");
                }
        );



    }




}
