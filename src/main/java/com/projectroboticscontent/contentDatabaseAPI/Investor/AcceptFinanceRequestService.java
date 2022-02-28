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

    private MongoClient hardwareClient;
    private ReactiveMongoTemplate hardwareMongoOps;

    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;

    private MongoClient investorClient;
    private ReactiveMongoTemplate investorMongoOps;

    public  AcceptFinanceRequestService() {



        this.hardwareClient = MongoClients.create();
        this.hardwareMongoOps = new ReactiveMongoTemplate(hardwareClient, "testHardwareDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient,"testLoginDatabase");

        this.investorClient = MongoClients.create();
        this.investorMongoOps = new ReactiveMongoTemplate(investorClient,"testInvestorDatabase");
    }

    private Mono SendCoin(String WALLET_ID, String RECEIVER, String AMOUNT)
    {
        WebClient webClient = WebClient.create();
        String url = Configuration.blockchainIPAddress + "/sol/transfer";

        return webClient
                .get()
                .uri(url+"?address="+WALLET_ID
                        +"&receiver="+RECEIVER
                        +"&amount="+AMOUNT)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(output ->{
                    return AddTransactionServiceStatic.AddTransaction3(RECEIVER, WALLET_ID, Long.valueOf(AMOUNT), 1);
                });
    }



    private Mono newHardware(String HARDWARE_ID, long MACHINE_OWNER, String WALLET_ID_INVESTOR, long PAYMENT,
                             String USER_WALLET_ID, long USAGE_TIME, long TOTAL_COST)
    {
        // long PRIMARY_KEY = JwtTokenUtil.parseJWT(token, USERTYPE);

        final String CURRENT_DATETIME = LocalDateTime.now().toString();
        long WORK_DONE = 0;
        long POWER_CONSUMPTION = 0;
        String STATUS = "Initialized";
        String TERMINATE_DATE_TIME = (LocalDateTime.now().plusDays(USAGE_TIME)).toString();


        final HardwareInfo submission = new HardwareInfo( HARDWARE_ID,  CURRENT_DATETIME, TERMINATE_DATE_TIME, WORK_DONE,
         POWER_CONSUMPTION, STATUS, MACHINE_OWNER,  WALLET_ID_INVESTOR,  PAYMENT,  USER_WALLET_ID, USAGE_TIME, TOTAL_COST);

        return hardwareMongoOps.insert(submission,"HardwareInfoCollection");


    }

    public Mono newHardwareWithWallet(String token, String USERTYPE, String HARDWARE_ID,  long PAYMENT,
                                      String USER_WALLET_ID, long USAGE_TIME, long TOTAL_COST)
    {
        long PRIMARY_KEY = JwtTokenUtil.parseJWT(token, USERTYPE);


        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));

        //  query.fields().include("WALLET_ID");
        //  query.fields().include("EMAIL");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output->{
                    return  newHardware( HARDWARE_ID, output.getPRIMARY_KEY(), output.getWALLET_ID(),  PAYMENT,
                            USER_WALLET_ID, USAGE_TIME, TOTAL_COST);
                })
                .switchIfEmpty(Mono.just("No User Found"));


    }

    private Mono UpdateRequest(long FINANCE_REQUEST_ID, long INVESTOR_PRIMARY_KEY, String INVESTOR_WALLET_ID)
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
                        FinanceRequest.class, "FinanceRequestCollection")
                .flatMap(output->{
                    return SendCoin(output.getINVESTOR_WALLET_ID(),
                            output.getSOLUTION_PROVIDER_WALLET_ID(), String.valueOf(output.getTOTAL_COST()));
                });

    }

    private Mono SelectRequest1 (String HARDWARE_ID, long FINANCE_REQUEST_ID,
                                 String token, String USERTYPE, long PAYMENT,
                                 String USER_WALLET_ID, long USAGE_TIME, long TOTAL_COST)
    {
        final long INVESTOR_PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(INVESTOR_PRIMARY_KEY)

                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");

       // String INVESTOR_WALLET_ID = "";

        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(
                    output->{
                        return UpdateRequest(FINANCE_REQUEST_ID,INVESTOR_PRIMARY_KEY, output.getWALLET_ID());
                    }
                )
                .flatMap(output->{
                    return newHardwareWithWallet( token,  USERTYPE,  HARDWARE_ID,   PAYMENT,
                            USER_WALLET_ID,  USAGE_TIME, TOTAL_COST);
            });

    }

    public Mono SelectRequest2 (long FINANCE_REQUEST_ID, String token, String USERTYPE)
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
