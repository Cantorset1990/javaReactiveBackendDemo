package com.projectroboticscontent.contentDatabaseAPI.Investor;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Hardware.HardwareInfo;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;

import com.projectroboticscontent.contentDatabaseAPI.TransactionHistory.AddTransactionServiceStatic;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class AcceptFinanceRequestService {

    private final MongoClient hardwareClient;
    private final ReactiveMongoTemplate hardwareMongoOps;

    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;

    private final MongoClient investorClient;
    private final ReactiveMongoTemplate investorMongoOps;

    public  AcceptFinanceRequestService() {


        this.hardwareClient = MongoClients.create();
        this.hardwareMongoOps = new ReactiveMongoTemplate(hardwareClient, "testHardwareDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient,"testLoginDatabase");

        this.investorClient = MongoClients.create();
        this.investorMongoOps = new ReactiveMongoTemplate(investorClient,"testInvestorDatabase");
    }

    public Mono<String> SendCoin(String WALLET_ID, String RECEIVER, String AMOUNT)
    {
        WebClient webClientCoin = WebClient.create();
        //String url = Configuration.blockchainIPAddress+"/sol/transfer" +"?address="+WALLET_ID
        //        +"&receiver="+RECEIVER
        //        +"&amount="+AMOUNT;
        String url = "http://localhost:8080/api/v1/sol/transfer?" +
                "address=" +
                WALLET_ID +
                "&receiver=" +
                RECEIVER +
                "&amount=" +
                AMOUNT;


        return webClientCoin
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

    }

    public Mono<String> SendCoin2(String token, String USERTYPE, String RECEIVER, String AMOUNT)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));



        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")

                .flatMap(output1->{
                    return SendCoin(output1.getWALLET_ID(),  RECEIVER,  AMOUNT);
                })
                .flatMap(
                        output2->{
                            return AddTransactionServiceStatic.AddTransaction4( token,  USERTYPE,  RECEIVER,
                                    Long.parseLong(AMOUNT), 1);
                        }
                )
                .switchIfEmpty(Mono.just("error"));


    }



    private Mono<HardwareInfo> newHardware(String HARDWARE_ID, long MACHINE_OWNER, String WALLET_ID_INVESTOR, long PAYMENT,
                             String WALLET_ID_USER, long USAGE_TIME, long TOTAL_COST, String WALLET_ID_SOLUTION_PROVIDER)
    {


        final String CURRENT_DATETIME = LocalDateTime.now().toString();
        long WORK_DONE = 0;
        long POWER_CONSUMPTION = 0;
        String STATUS = "Initialized";
        String TERMINATE_DATE_TIME = (LocalDateTime.now().plusDays(USAGE_TIME)).toString();


        final HardwareInfo submission = new HardwareInfo( HARDWARE_ID,  CURRENT_DATETIME, TERMINATE_DATE_TIME, WORK_DONE,
         POWER_CONSUMPTION, STATUS, MACHINE_OWNER,  WALLET_ID_INVESTOR,  PAYMENT,  WALLET_ID_USER,
                USAGE_TIME, TOTAL_COST, WALLET_ID_SOLUTION_PROVIDER);

        return hardwareMongoOps.insert(submission,"HardwareInfoCollection");


    }

    public Mono<HardwareInfo> newHardwareWithWallet(String token, String USERTYPE, String HARDWARE_ID,  long PAYMENT,
                                      String USER_WALLET_ID, long USAGE_TIME,
                                                    long TOTAL_COST, String WALLET_ID_SOLUTION_PROVIDER)
    {
        long PRIMARY_KEY = JwtTokenUtil.parseJWT(token, USERTYPE);


        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));




        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output->{
                    return  newHardware( HARDWARE_ID, output.getPRIMARY_KEY(), output.getWALLET_ID(),  PAYMENT,
                            USER_WALLET_ID, USAGE_TIME, TOTAL_COST, WALLET_ID_SOLUTION_PROVIDER);
                });



    }

    private Mono<FinanceRequest> UpdateRequest(long FINANCE_REQUEST_ID, long INVESTOR_PRIMARY_KEY, String INVESTOR_WALLET_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("FINANCE_REQUEST_ID").is(FINANCE_REQUEST_ID)

                        ));



        Update update = new Update();
        update.set("STATUS", 1);
        update.set("INVESTOR_PRIMARY_KEY", INVESTOR_PRIMARY_KEY);
        update.set("INVESTOR_WALLET_ID",INVESTOR_WALLET_ID);

        return investorMongoOps.findAndModify(query,update,
                        FinanceRequest.class, "FinanceRequestCollection");


    }

    private Mono<String> SelectRequest1 (String HARDWARE_ID, long FINANCE_REQUEST_ID,
                                 String token, String USERTYPE, long PAYMENT,
                                 String USER_WALLET_ID, long USAGE_TIME, long TOTAL_COST)
    {
        final long INVESTOR_PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(INVESTOR_PRIMARY_KEY)

                        ));


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(
                    output->{
                        return UpdateRequest(FINANCE_REQUEST_ID,INVESTOR_PRIMARY_KEY, output.getWALLET_ID());
                    }
                )
                .flatMap(output->{
                    return newHardwareWithWallet( token,  USERTYPE,  HARDWARE_ID,   PAYMENT,
                            USER_WALLET_ID,  USAGE_TIME, TOTAL_COST, output.getSOLUTION_PROVIDER_WALLET_ID());
                 })
                .flatMap(output->{
                    return SendCoin2(token, USERTYPE,
                            output.getWALLET_ID_SOLUTION_PROVIDER(), String.valueOf(TOTAL_COST));
                })
                .switchIfEmpty(Mono.just("error"));

    }

    public Mono<String> SelectRequest2 (long FINANCE_REQUEST_ID, String token, String USERTYPE)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("FINANCE_REQUEST_ID").is(FINANCE_REQUEST_ID)

                        ));

        return investorMongoOps.findOne(query,FinanceRequest.class, "FinanceRequestCollection")
                .filter(output -> output.getSTATUS() == 0)
                .flatMap(
                        output->{
                            return SelectRequest1(output.getHARDWARE_ID(),  FINANCE_REQUEST_ID,
                             token,  USERTYPE, output.getPAYMENT(), output.getUSER_WALLET_ID(),
                                    output.getUSAGE_TIME(), output.getTOTAL_COST());
                        }
                )
                .switchIfEmpty(Mono.just("Request already funded"));
    }



}
