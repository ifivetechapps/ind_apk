package com.ifiveuv.indsmart.Connectivity;

import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model.PurchaseEnquiryData;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.Model.RequisitionHeader;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Model.SaleItemList;
import com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryItemModel;
import com.ifiveuv.indsmart.UI.Sales.SalesInvoice.Model.InvoiceItemList;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserAPICall {
    @POST("public/api-login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

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


}
