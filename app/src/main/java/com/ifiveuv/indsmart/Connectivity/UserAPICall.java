package com.ifiveuv.indsmart.Connectivity;


import com.ifiveuv.indsmart.UI.Masters.Model.CustomerList;
import com.ifiveuv.indsmart.UI.Masters.Model.SupplierList;
import com.ifiveuv.indsmart.UI.Masters.UomSave;

import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model.PurchaseEnquiryData;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.Model.RequisitionHeader;
import com.ifiveuv.indsmart.UI.Sales.OnlineModel.SalesOrderAll;
import com.ifiveuv.indsmart.UI.Sales.OnlineModel.SalesQuoteOrderAll;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Model.SaleItemList;
import com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryItemModel;
import com.ifiveuv.indsmart.UI.Sales.OnlineModel.SalesEnquiryOrderAll;
import com.ifiveuv.indsmart.UI.Sales.SalesInvoice.Model.InvoiceItemList;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemList;
import com.ifiveuv.indsmart.UI.SalesApprove.ApproveRequest;
import com.ifiveuv.indsmart.UI.SalesApprove.InvoiceItemApprove;
import com.ifiveuv.indsmart.UI.SalesApprove.SaleItemListApprove;
import com.ifiveuv.indsmart.UI.SalesApprove.SalesEnquiryList;
import com.ifiveuv.indsmart.UI.SalesApprove.SoQuoteApprove;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserAPICall {
    @POST("public/api-login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("public/api-save-uom")
    Call<LoginResponse> uomsave(@Header("token") String token, @Body UomSave uomSave);

    @POST("public/api-save-supplier")
    Call<LoginResponse> suppliersave(@Header("token") String token, @Body SupplierList uomSave);

    @POST("public/api-save-customer")
    Call<LoginResponse> customersave(@Header("token") String token, @Body CustomerList uomSave);

    @POST("public/api-so-order-list")
    Call<SaleItemListApprove> saleOrderList(@Header("token") String token);

    @POST("public/api-so-invoice-list")
    Call<InvoiceItemApprove> saleinvoiceList(@Header("token") String token);

    @POST("public/api-so-quote-list")
    Call<SoQuoteApprove> sqList(@Header("token") String token);

    @POST("public/api-so-enquiry-list")
    Call<SalesEnquiryList> seList(@Header("token") String token);

    @POST("public/api-so-order-approve")
    Call<CommanResponse> soApprove(@Header("token") String token, @Body ApproveRequest approveRequest);

    @POST("public/api-so-quote-approve")
    Call<CommanResponse> sqApprove(@Header("token") String token, @Body ApproveRequest approveRequest);

    @POST("public/api-so-enquiry-approve")
    Call<CommanResponse> seApprove(@Header("token") String token, @Body ApproveRequest approveRequest);

    @POST("public/api-so-invoice-approve")
    Call<CommanResponse> siApprove(@Header("token") String token, @Body ApproveRequest approveRequest);

    @POST("esunmr/public/api-update-fcm-token")
    Call<LoginResponse> updateFCMToken(@Header("token") String token, @Body LoginRequest loginRequest);

    @GET("public/api-alldatas")
    Call<AllDataList> allDataList(@Header("token") String token);

    @POST("public/api-save-so-enquiry")
    Call<EnquiryResponse> sendEnquirySingleData(@Header("token") String token, @Body EnquiryItemModel enquiryItemModel);


    @POST("public/api-save-so-quote")
    Call<EnquiryResponse> sendQuoteSingleData(@Header("token") String token, @Body QuoteItemList quoteItemList);


    @POST("public/api-save-so-order")
    Call<EnquiryResponse> sendSalesSingleData(@Header("token") String token, @Body SaleItemList saleItemList);


    @POST("public/api-save-so-invoice")
    Call<EnquiryResponse> sendSalesInvoiceSingleData(@Header("token") String token, @Body InvoiceItemList invoiceItemList);


    @POST("public/api-save-po-req")
    Call<EnquiryResponse> sendPurchaseReqSingleData(@Header("token") String token, @Body RequisitionHeader saleItemList);

    @POST("public/api-poreq-approve")
    Call<Message> purchaseReqApprovedData(@Header("token") String token, @Body SendApprovalId sendApprovalId);

    @POST("public/api-save-po-enq")
    Call<EnquiryResponse> sendPurachseEnquirySingleData(@Header("token") String token, @Body PurchaseEnquiryData saleItemList);

    @POST("public/api-so-enquiry-details")
    Call<SalesEnquiryOrderAll> allenquiry(@Header("token") String token);
    @POST("public/api-so-quote-details")
    Call<SalesQuoteOrderAll> allquote(@Header("token") String token);
    @POST("public/api-so-order-details")
    Call<SalesOrderAll> allsales(@Header("token") String token);

}
