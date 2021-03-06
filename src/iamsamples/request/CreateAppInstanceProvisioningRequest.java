package iamsamples.request;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import oracle.iam.api.OIMService;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.utils.vo.OIMType;
import oracle.iam.request.vo.Beneficiary;
import oracle.iam.request.vo.RequestBeneficiaryEntity;
import oracle.iam.request.vo.RequestBeneficiaryEntityAttribute;
import oracle.iam.request.vo.RequestConstants;
import oracle.iam.request.vo.RequestData;
import oracle.iam.vo.OperationResult;


public class CreateAppInstanceProvisioningRequest {
	public static void main(String[] args) throws Exception {

        Hashtable env = new Hashtable();
        env.put("java.naming.provider.url", "t3://localhost:8003/oim");
        env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");

        OIMClient oimClient = new OIMClient(env);
        oimClient.login("xelsysadm", "Welcome1");

        OIMService unifiedService = oimClient.getService(OIMService.class);
        
        
        RequestData requestData = new RequestData();

        RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
        requestEntity.setRequestEntityType(OIMType.ApplicationInstance);
        requestEntity.setEntitySubType("NetworkServer1");
        requestEntity.setEntityKey("1"); 
        requestEntity.setOperation(RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION);
        
        List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
        RequestBeneficiaryEntityAttribute attr = new RequestBeneficiaryEntityAttribute("Account Name", "FEDERER", RequestBeneficiaryEntityAttribute.TYPE.String); 
        attrs.add(attr);
        attr = new RequestBeneficiaryEntityAttribute("Password", "Welcome1", RequestBeneficiaryEntityAttribute.TYPE.String);
        attrs.add(attr);
        
        requestEntity.setEntityData(attrs);
        
        List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
        entities.add(requestEntity);
        
        Beneficiary beneficiary = new Beneficiary();
        
        String userKey = "14"; 
        beneficiary.setBeneficiaryKey(userKey);
        beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY);        
        beneficiary.setTargetEntities(entities);
        
        List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
        beneficiaries.add(beneficiary);
        requestData.setBeneficiaries(beneficiaries);
        
        OperationResult result = unifiedService.doOperation(requestData, OIMService.Intent.REQUEST);

        System.out.println("result = " + result.toString());
        
        System.exit(0);
    		
	}
}
