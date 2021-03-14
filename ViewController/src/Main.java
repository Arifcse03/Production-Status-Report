import model.services.AppModuleImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewObject;

public class Main {
    private RichTable mainTable;

    public Main() {
    }
    /*** to access the appmodule**/
    public AppModuleImpl getAppModuleImpl() {
        DCBindingContainer bindingContainer =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        //BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContainer.findDataControl("AppModuleDataControl"); // Name of application module in datacontrolBinding.cpx
        AppModuleImpl appM = (AppModuleImpl)dc.getDataProvider();
        return appM;
    }
    AppModuleImpl am =  getAppModuleImpl();
    
    
    
    
    
    public String action_call() {
        // Add event code here...
        ViewObject searchVO=am.getSearchVO1();
        String season=null;
        int Buyer=0;
       String org=null;
        try{
            season=searchVO.getCurrentRow().getAttribute("Season").toString();
        }
        catch(Exception e) {
            season=null;
        }
        try{
            Buyer=Integer.parseInt(searchVO.getCurrentRow().getAttribute("BuyerId").toString());
        }
        catch(Exception e) {
            Buyer=0;
        }
        try{
            org=searchVO.getCurrentRow().getAttribute("Org").toString();
        }
        catch(Exception e) {
           org=null;
        }
        
        System.out.println("org.............."+org);
        System.out.println("Buyer............."+Buyer);
        
        System.out.println("Season.............."+season);
            String value ="failed";
        
        int pram=1;
        am.getDBTransaction().commit();
        ViewObject oder=am.getMainView1();
        oder.setNamedWhereClauseParam("param",pram);
        oder.setWhereClause("SEASON = '"+season+"' AND BUYER_ID = '"+Buyer+"' AND PRODUCTION_UNIT= '"+org+"'");
        
      
        oder.executeQuery();
        
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(mainTable);
        
        
        
        return null;
    }

    public void setMainTable(RichTable mainTable) {
        this.mainTable = mainTable;
    }

    public RichTable getMainTable() {
        return mainTable;
    }
}
