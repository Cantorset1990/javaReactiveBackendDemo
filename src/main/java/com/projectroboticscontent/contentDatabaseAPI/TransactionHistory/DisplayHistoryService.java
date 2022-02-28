package com.projectroboticscontent.contentDatabaseAPI.TransactionHistory;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionDataRetrieval;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DisplayHistoryService {

    private  MongoClient historyClient;
    private  ReactiveMongoTemplate historyMongoOps ;

    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;


    public DisplayHistoryService() {

        historyClient = MongoClients.create();

        historyMongoOps = new ReactiveMongoTemplate(historyClient,
                "testTransactionHistoryDatabase");


        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }


    private Flux getInflow(String RECEIVER_WALLET_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("RECEIVER_WALLET_ID").is(RECEIVER_WALLET_ID)

                        ));

        return historyMongoOps.find(query, HistoryInfo.class,"HistoryCollection");


    }

    private Flux getOutflow(String SENDER_WALLET_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SENDER_WALLET_ID").is(SENDER_WALLET_ID)

                        ));

        return historyMongoOps.find(query, HistoryInfo.class,"HistoryCollection");


    }


    private Flux getInflowRobot(String RECEIVER_WALLET_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("RECEIVER_WALLET_ID").is(RECEIVER_WALLET_ID),
                                Criteria.where("PROPERTY").is(2)
                        ));

        return historyMongoOps.find(query, HistoryInfo.class,"HistoryCollection");


    }

    private Flux getOutflowRobot(String SENDER_WALLET_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SENDER_WALLET_ID").is(SENDER_WALLET_ID),
                                Criteria.where("PROPERTY").is(2)
                        ));

        return historyMongoOps.find(query, HistoryInfo.class,"HistoryCollection");


    }


    private Flux getInflowNonRobot(String RECEIVER_WALLET_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("RECEIVER_WALLET_ID").is(RECEIVER_WALLET_ID),
                                Criteria.where("PROPERTY").is(1)
                        ));

        return historyMongoOps.find(query, HistoryInfo.class,"HistoryCollection");


    }

    private Flux getOutflowNonRobot(String SENDER_WALLET_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SENDER_WALLET_ID").is(SENDER_WALLET_ID),
                                Criteria.where("PROPERTY").is(1)
                        ));

        return historyMongoOps.find(query, HistoryInfo.class,"HistoryCollection");


    }

    public Flux viewInflow(String token, String USERTYPE){

        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));



        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMapMany(

                        output-> {

                            return getInflow(output.getWALLET_ID());

                        }

                );



    }

    public Flux viewOutflow(String token, String USERTYPE){


        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));



        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMapMany(

                        output-> {

                           return getOutflow(output.getWALLET_ID());

                        }

                );


    }

    public Flux viewInflowRobot(String token, String USERTYPE){

        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));



        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMapMany(

                        output-> {

                            return getInflowRobot(output.getWALLET_ID());

                        }

                );



    }

    public Flux viewOutflowRobot(String token, String USERTYPE){


        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));



        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMapMany(

                        output-> {

                            return getOutflowRobot(output.getWALLET_ID());

                        }

                );


    }


    public Flux viewInflowNonRobot(String token, String USERTYPE){

        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));



        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMapMany(

                        output-> {

                            return getInflowNonRobot(output.getWALLET_ID());

                        }

                );



    }

    public Flux viewOutflowNonRobot(String token, String USERTYPE){


        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));



        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMapMany(

                        output-> {

                            return getOutflowNonRobot(output.getWALLET_ID());

                        }

                );


    }




}
