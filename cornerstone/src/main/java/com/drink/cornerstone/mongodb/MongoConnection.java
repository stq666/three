package com.drink.cornerstone.mongodb;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by newroc on 14-4-1.
 */
public class MongoConnection {
    public static final String READPREFERENCE_PRIMARY="primary";
    public static final String READPREFERENCE_SECONDARY="secondary";
    public static final String READPREFERENCE_SECONDARY_PREFERRED="secondaryPreferred";
    public static final String READPREFERENCE_PRIMARY_PREFERRED="primaryPreferred";
    public static final String READPREFERENCE_NEAREST="nearest";

    /**
     * 服务器地址列表
     */
    private List<ServerAddress> serverAddressList;

    private List<Credential> credentials=new ArrayList<Credential>();
    private MongoClient mongoClient;
    private String defaultReadPreference =null;

    private String requiredReplicaSetName;

    public List<MongoCredential> getCredentialsList() {
        return mongoClient.getCredentialsList();
    }

    public String getConnectPoint() {
        return mongoClient.getConnectPoint();
    }

    public int getOptions() {
        return mongoClient.getOptions();
    }

    public void resetOptions() {
        mongoClient.resetOptions();
    }

    public MongoClientOptions getMongoClientOptions() {
        return mongoClient.getMongoClientOptions();
    }

    public int getMaxBsonObjectSize() {
        return mongoClient.getMaxBsonObjectSize();
    }

    public CommandResult fsyncAndLock() {
        return mongoClient.fsyncAndLock();
    }

    public boolean isLocked() {
        return mongoClient.isLocked();
    }

    public ServerAddress getAddress() {
        return mongoClient.getAddress();
    }

    public DB getDB(String dbname) {
        return mongoClient.getDB(dbname);
    }

    public DBObject unlock() {
        return mongoClient.unlock();
    }

    public ReadPreference getReadPreference() {
        return mongoClient.getReadPreference();
    }



    public String getVersion() {
        return mongoClient.getVersion();
    }

    public void close() {
        mongoClient.close();
    }

    public Collection<DB> getUsedDatabases() {
        return mongoClient.getUsedDatabases();
    }

    public void dropDatabase(String dbName) {
        mongoClient.dropDatabase(dbName);
    }

    public void setWriteConcern(WriteConcern concern) {
        mongoClient.setWriteConcern(concern);
    }

    public ReplicaSetStatus getReplicaSetStatus() {
        return mongoClient.getReplicaSetStatus();
    }

    public CommandResult fsync(boolean async) {
        return mongoClient.fsync(async);
    }

    public List<ServerAddress> getAllAddress() {
        return mongoClient.getAllAddress();
    }

    public List<String> getDatabaseNames() {
        return mongoClient.getDatabaseNames();
    }

    public void addOption(int option) {
        mongoClient.addOption(option);
    }

    public void setReadPreference(ReadPreference preference) {
        mongoClient.setReadPreference(preference);
    }

    public WriteConcern getWriteConcern() {
        return mongoClient.getWriteConcern();
    }

    public void setOptions(int options) {
        mongoClient.setOptions(options);
    }

    public void init(){
        //MongoClientOptions options=MongoClientOptions.builder().defaultReadPreference(ReadPreference.secondaryPreferred()).requiredReplicaSetName("rs1").build();

        MongoClientOptions.Builder builder=MongoClientOptions.builder();
        if(requiredReplicaSetName!=null&&requiredReplicaSetName.trim().length()>0){
            builder.requiredReplicaSetName(requiredReplicaSetName);
        }

        //设置读写
        if(defaultReadPreference !=null){
          if(READPREFERENCE_PRIMARY.equals(defaultReadPreference)) {
              builder.readPreference(ReadPreference.primary());
          }else if(READPREFERENCE_SECONDARY.equals(defaultReadPreference)){
              builder.readPreference(ReadPreference.secondary());
          } else if(READPREFERENCE_PRIMARY_PREFERRED.equals(defaultReadPreference)){
              builder.readPreference(ReadPreference.primaryPreferred());
          } else if(READPREFERENCE_SECONDARY_PREFERRED.equals(defaultReadPreference)){
              builder.readPreference(ReadPreference.secondaryPreferred());
          } else if(READPREFERENCE_NEAREST.equals(defaultReadPreference)){
              builder.readPreference(ReadPreference.nearest());
          }
        }


        MongoClientOptions options=builder.build();
        if(credentials!=null&&!credentials.isEmpty()){
            List<MongoCredential> credentialsList=new ArrayList<MongoCredential>();
            for (int i = 0; i <credentials.size() ; i++) {
                Credential credential= credentials.get(i);
                MongoCredential mongoCredential;
                if(credential.getMechanism()==null||MongoCredential.MONGODB_CR_MECHANISM.equals(credential.getMechanism())){
                    mongoCredential = MongoCredential.createMongoCRCredential(credential.getUserName(), credential.getSource(), credential.getPassword().toCharArray());
                    credentialsList.add(mongoCredential);
                }else if(MongoCredential.GSSAPI_MECHANISM.equals(credential.getMechanism())){
                    //TODO 暂时不处理
                }else if(MongoCredential.MONGODB_X509_MECHANISM.equals(credential.getMechanism())){
                    //TODO 暂时不处理
                }else if(MongoCredential.PLAIN_MECHANISM.equals(credential.getMechanism())){
                    //TODO 暂时不处理
                }else{
                    throw new IllegalArgumentException("Unsupported authentication protocol: " + credential.getMechanism());

                }

            }
            mongoClient=new MongoClient(serverAddressList,credentialsList,options);
        }else{
            mongoClient=new MongoClient(serverAddressList,options);
        }





    }


    public List<ServerAddress> getServerAddressList() {
        return serverAddressList;
    }

    public void setServerAddressList(List<ServerAddress> serverAddressList) {
        this.serverAddressList = serverAddressList;
    }

    public List<Credential> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<Credential> credentials) {
        this.credentials = credentials;
    }

    public String getDefaultReadPreference() {
        return defaultReadPreference;
    }

    public void setDefaultReadPreference(String defaultReadPreference) {
        this.defaultReadPreference = defaultReadPreference;
    }

    public String getRequiredReplicaSetName() {
        return requiredReplicaSetName;
    }


    public void setRequiredReplicaSetName(String requiredReplicaSetName) {
        this.requiredReplicaSetName = requiredReplicaSetName;
    }
}
