package com.projectroboticscontent.contentDatabaseAPI.Utility;

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
public class UpdateFormPermissionService {

    private final MongoClient client;
    private final ReactiveMongoTemplate MongoOps;

    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;

    public UpdateFormPermissionService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error\n");
    }

    public Mono<String> createStatusException2(long id){
        return
                Mono.just(String.valueOf(id));
    }

    public Mono<ResponseStatusException> Error(){

        return Mono.just(createStatusException());

    }

    public Mono<String> updateMessage(){

        return Mono.just("Permission Update was successful");
    }

    public Mono<long[]> addPermission(long[] FORM_PERMISSION,  long FORM_ID)
    {
        long newarr[] = new long[FORM_PERMISSION.length + 1];

        for (int i = 0; i < FORM_PERMISSION.length; i++)
            newarr[i] = FORM_PERMISSION[i];

        newarr[FORM_PERMISSION.length] = FORM_ID;

        return Mono.just(newarr);
    }

    public Mono<long[]> deletePermission(long[] FORM_PERMISSION,  long FORM_ID)
    {
        long newarr[] = new long[FORM_PERMISSION.length];

        for (int i = 0; i < FORM_PERMISSION.length; i++) {
            if ( FORM_PERMISSION[i] != FORM_ID) {
                newarr[i] = FORM_PERMISSION[i];
            } else {
                newarr[i] = 0;
            }
        }

       // newarr[FORM_PERMISSION.length] = FORM_ID;

        return Mono.just(newarr);
    }

    public Mono<String> getAndUpdatePermission(String token, String USERTYPE, long FORM_ID)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

       // final long FORM_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")

                .flatMap(output ->{
                    return addPermission(output.getFORM_PERMISSION(), FORM_ID);
                })
                .flatMap(
                        output->{
                            return updatePermission(token, (long[]) output,USERTYPE);
                        }
                );

               // .switchIfEmpty(Error());
    }

    public Mono<String> getAndDeletePermission(String token, String USERTYPE, long FORM_ID)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        // final long FORM_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")

                .flatMap(output ->{
                    return deletePermission(output.getFORM_PERMISSION(), FORM_ID);
                })
                .flatMap(
                        output->{
                            return updatePermission(token, (long[]) output,USERTYPE);
                        }
                );

               // .switchIfEmpty(Error());
    }

    public Mono<String> updatePermission(String token, long[] FORM_PERMISSION, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)
                               // Criteria.where("FORM_ID").is(formID)
                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");

        Update update = new Update();
        update.set("FORM_PERMISSION", FORM_PERMISSION);
        //update.set("FORM_TITLE", FORM_TITLE);

        return registrationMongoOps.findAndModify(query,update, UserProfile.class,"RegistrationCollection")
                .flatMap(output->{return updateMessage();});
               // .switchIfEmpty(Error());


    }
}
