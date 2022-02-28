package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

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
public class RetrieveUserFormService {


    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;

    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;

    public RetrieveUserFormService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Error\n");
    }

    public Mono Error(){

      //  return Mono.just(createStatusException());

        return Mono.just("No forms found");

    }

    public Mono formNotFoundMessage(){

        return Mono.just("Form not found");

    }

    public Flux getAllForms(String token, String USERTYPE)
    {


            final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

            final Query query =
                    Query.query(
                            new Criteria().andOperator(
                                    Criteria.where("USER_PRIMARY_KEY").is(PRIMARY_KEY)

                            ));


            return MongoOps.find(query, FormDataRetrieval.class,"UserRequestCollection")
                    .switchIfEmpty(Error());





    }

    public Flux getAllFormsStatus(String token, String USERTYPE, long STATUS)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("USER_PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("STATUS").is(STATUS)

                        ));

        /*
        return checkProfile(token, USERTYPE)
                .flatMapMany(output->{

                    return MongoOps.find(query, FormDataRetrieval.class,"UserRequestCollection")
                            .switchIfEmpty(Error());
                        }
                );

         */

        return MongoOps.find(query, FormDataRetrieval.class,"UserRequestCollection")
                .switchIfEmpty(Error());


        // query.fields().include("FORM_ID");
        // query.fields().include("JSON_FORM_DATA");
        // query.fields().include("FORM_TITLE");

        // return MongoOps.findAll(FormInfo.class,"UserRequestCollection")
        //  ;

        //  return checkProfile(token, USERTYPE);




    }

    public Mono getOneForm(String token, long FORM_ID, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("USER_PRIMARY_KEY").is(ID),
                                Criteria.where("FORM_ID").is(FORM_ID)
                        ));

      //  query.fields().include("FORM_ID");
      //  query.fields().include("JSON_FORM_DATA");
      //  query.fields().include("FORM_TITLE");

        /*
        return checkProfile(token, USERTYPE)
                .flatMap(output->{

                    return MongoOps.findOne(query, FormDataRetrieval.class,"UserRequestCollection")
                            .switchIfEmpty(Error());

                });

         */

        return MongoOps.findOne(query, FormDataRetrieval.class,"UserRequestCollection")
                .switchIfEmpty(Error());



    }

    public Mono checkProfile(String token, String USERTYPE)
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

    public Flux viewAllUserForms()
    {

        /*
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));

         */



        return MongoOps.findAll(FormDataRetrieval.class,"UserRequestCollection")
                .switchIfEmpty(Error());


    }

    public Flux viewAllUserFormsStatus(String token, String USERTYPE, long STATUS)
    {


        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("STATUS").is(STATUS)

                        ));



        return checkProfile(token, USERTYPE)
                .flatMapMany(output->{

                    return MongoOps.find(query, FormDataRetrieval.class,"UserRequestCollection")
                            .switchIfEmpty(Error());

                });




    }

    private Mono viewSingleForm(long FORM_ID)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(

                                Criteria.where("FORM_ID").is(FORM_ID)
                        ));

        return MongoOps.findOne(query, FormDataRetrieval.class,"UserRequestCollection")
                .switchIfEmpty(Error());
    }

    public  Flux viewAllUserFormsByProvider(String token, String USERTYPE)
    {
       // final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);


        return checkProfile(token, USERTYPE)
                .flatMapMany(output->{
                   return viewAllUserForms();
                });



       // return viewAllUserForms(ID);
    }

    public  Mono viewSingleFormByProvider(String token, String USERTYPE, long FORM_ID)
    {
      //  final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);


        return checkProfile(token, USERTYPE)
                .flatMap(output->{
                    return viewSingleForm(FORM_ID);
                });




    }

}
