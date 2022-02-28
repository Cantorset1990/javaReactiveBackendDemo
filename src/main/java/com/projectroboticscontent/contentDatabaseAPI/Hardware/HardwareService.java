package com.projectroboticscontent.contentDatabaseAPI.Hardware;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionInput;
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
public class HardwareService {

    private MongoClient hardwareClient;
    private ReactiveMongoTemplate hardwareMongoOps;

    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;

   // private MongoClient investorClient;
   // private ReactiveMongoTemplate investorMongoOps;

    public HardwareService() {

        this.hardwareClient = MongoClients.create();
        this.hardwareMongoOps = new ReactiveMongoTemplate(hardwareClient, "testHardwareDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient,"testLoginDatabase");

      //  this.investorClient = MongoClients.create();
      //  this.investorMongoOps = new ReactiveMongoTemplate(investorClient,"testInvestorDatabase");
    }

    private Mono newHardware(String HARDWARE_ID, long MACHINE_OWNER, String WALLET_ID_INVESTOR, long PAYMENT,
                             String USER_WALLET_ID, long USAGE_TIME, long TOTAL_COST)
    {
        // long PRIMARY_KEY = JwtTokenUtil.parseJWT(token, USERTYPE);

        final String CURRENT_DATETIME = LocalDateTime.now().toString();
        long WORK_DONE = 0;
        long POWER_CONSUMPTION = 0;
        String STATUS = "Initialized";
        String TERMINATE_DATE_TIME = "";


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
                    return  newHardware( HARDWARE_ID, PRIMARY_KEY, output.getWALLET_ID(),
                            PAYMENT,  USER_WALLET_ID, USAGE_TIME, TOTAL_COST);
                })
                .switchIfEmpty(Mono.just("No User Found"));


    }

    private Mono SendCoin(String WALLET_ID, String RECEIVER, String AMOUNT)
    {
        WebClient webClient = WebClient.create();
        String url = Configuration.blockchainIPAddress +"/sol/transfer";

        return webClient
                .get()
                .uri(url+"?address="+WALLET_ID
                        +"&receiver="+RECEIVER
                        +"&amount="+AMOUNT)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(output ->{
                    return AddTransactionServiceStatic.AddTransaction3(RECEIVER, WALLET_ID, Long.valueOf(AMOUNT), 2);
                });
    }

    private Mono updateHardwareWork(String HARDWARE_ID, long TOTAL_WORK, long WORK_DONE)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("HARDWARE_ID").is(HARDWARE_ID)

                        ));

        Update update = new Update();
        update.set("WORK_DONE", TOTAL_WORK);

        return hardwareMongoOps.findAndModify(query,update, HardwareInfo.class,"HardwareInfoCollection")
                .flatMap(output->{
                    return  SendCoin(output.getWALLET_ID_USER(), output.getWALLET_ID_INVESTOR(),
                            String.valueOf(WORK_DONE* output.getPAYMENT()));
                });
    }

    public Mono updateHardwareWorkIncrement(String HARDWARE_ID, long WORK_DONE)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("HARDWARE_ID").is(HARDWARE_ID)

                        ));

        return hardwareMongoOps.findOne(query, HardwareInfo.class,"HardwareInfoCollection")
                .flatMap(output->{
                    return updateHardwareWork(HARDWARE_ID, output.getWORK_DONE() + WORK_DONE, WORK_DONE);
                });





    }




    public Mono updateHardwareStatus(String HARDWARE_ID, String STATUS)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("HARDWARE_ID").is(HARDWARE_ID)

                        ));

        Update update = new Update();
        update.set("STATUS", STATUS);

        return hardwareMongoOps.findAndModify(query,update, SolutionInput.class,"HardwareInfoCollection");
    }

    private Mono updateHardwarePower(String HARDWARE_ID, long POWER_CONSUMPTION)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("HARDWARE_ID").is(HARDWARE_ID)

                        ));

        Update update = new Update();
        update.set("POWER_CONSUMPTION", POWER_CONSUMPTION);

        return hardwareMongoOps.findAndModify(query,update, SolutionInput.class,"HardwareInfoCollection");
    }

    public Mono updateHardwarePowerIncrement(String HARDWARE_ID, long POWER_CONSUMPTION)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("HARDWARE_ID").is(HARDWARE_ID)

                        ));

        return hardwareMongoOps.findOne(query, HardwareInfo.class,"HardwareInfoCollection")
                .flatMap(output->{
                    return updateHardwarePower(HARDWARE_ID, output.getPOWER_CONSUMPTION() + POWER_CONSUMPTION);
                });

    }




}
