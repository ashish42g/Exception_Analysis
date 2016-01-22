package exceptionPigUdf;

public class TestingUdf {

    public static void main(String[] args) {

        //String input ="(2015-11-06 03:19:11,839 SEVERE [com.mitchell.services.core.errorlog.server.SyslogWriter] (Thread-1595 (HornetQ-client-global-threads-2107964479)) %%100101320047%%159200%%FATAL%%ANKITSAXENA%%USRMGT%%RESOURCE_MANAGEMENT%%2015-11-06T16:49:11.082+05:30%%com.mitchell.resourcemanagement.dao.WorkGroupsDAO%%getWorkGroupManager%%%%%%%%[jboss_jbc7] Oracle ExceptionORA-20080: Invalid input combination of Company Code, Workgroup Id, and Workgroup Type-ORA-06512: at , line 1395-ORA-06512: at line 1)";
        String input ="(2015-11-05 22:23:00,989 SEVERE [com.mitchell.services.technical.workcenter.edog.pojo.EDOGManagerImpl] (Thread-235 (HornetQ-client-global-threads-911984531)) Exception occured in edogmanagerimpl.evaluate() com.mitchell.common.exception.MitchellException - Type: 159507, ErrorDateTime: Thu Nov 05 22:23:00 PST 2015, ClassName: com.mitchell.services.technical.workcenter.edog.pojo.EDOGManagerImpl, MethodName: evaluate, SeverName: dapp11lxv.mitchell.com, WorkItemId: 14bffc7c-b5c4-48b4-a9d5-942d3dbf97c3, Description: Error in evaluating the MWM doc)";



        ExceptionUDF testUdf = new ExceptionUDF();
        String result = testUdf.parse(input);
        System.out.println(result);
    }
}
