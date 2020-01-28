@extends('layouts.header')
@section('content')

<style type="text/css">
  @media  only screen and (min-width: 1500px) {
    .bulk_budget_line_id {width: 160px !important;}
    .bulk_line_no {width: 160px !important;}
    .bulk_account_code {width: 135px !important;}
    .bulk_account_code_meaning {width: 210px !important;}
    .bulk_description {width: 180px !important;}
    .bulk_active {width: 140px !important;}
  }
  .bulk_budget_line_id {width: 60px;}
  .bulk_line_no {width: 60px;}
  .bulk_account_code {width: 135px;}
  .bulk_account_code_meaning {width: 210px;}
  .bulk_description {width: 180px;}
  .bulk_active {width: 140px;}
  .align{
   background:#71B3FF;
   letter-spacing:2px;
   text-align:center;
   color:#000;
 }
 .align1{
   background:#71B3FF;
   text-align:center;
   color:#000;
   padding:5px 13px !important;
 }
</style>
<div class="ajaxLoading"></div>
<form action="" method="post" class="acc_code_form" id="acc_code_form" data-parsley-validate>
  <input type="hidden" value="" name="savestatus" id="savestatus" />
  {{ csrf_field()}}
  <h4 class="heads">WBS<span class="ui_close_btn"><a href="{{URL::to('accountcodesnew')}}" class="collapse-close pull-right btn-danger"></a></span></h4>
  <div class="card">
    <div class="card-header">
      <div class="row">
        <div class="col-md-6">
          <label class="align"> Project: {{$project_name}}</label>
        </div>
        <div class="col-md-6">
          <label class="align">Job Code: {{$project_code}}  </label>
        </div>
      </div>

    </div>
    <div class="card-body card-block">
      <!------------------------------------- Body content start here ---------------------------->
      <div class="row">
        <div class="col-md-12">
         <div class="form-group  col-md-4 row" style="display:none;">
          <label for="inputIsValid" class="form-control-label col-md-5">Project  Name</label>
          <div class="col-md-7">
            <input type="text" id="project_id" name="project_id" class="form-control 	project_id"  value="{{$project_id}}" readonly>
            <select name='project_id' rows='5' class='form-control project_id' style="pointer-events:none;">
             {!! $project_id !!}
           </select>
         </div>
       </div>
       <div class="form-group  col-md-4 row" style="display:none;">
        <label for="inputIsValid" class="form-control-label col-md-5">Main Account Code</label>
        <div class="col-md-7">
          <input type="text" id="account_code" name="account_code" class="form-control 	account_code"  value="{{$project_code}}" readonly>
        </div>
      </div>  
      <div class="form-group  col-md-4 row" style="display:none;">
        <label for="inputIsValid" class="form-control-label col-md-5">Active</label>
        <div class="col-md-7">
          <select name="active" class="form-control active">

            <option value="Yes">Yes</option>
            <option value="No" >No</option>
          </select>
        </div>
      </div>
    </div>	
  </div>


  <!------------------------------------------------------------------------------------------>
  <div class="row">

    <div class="col-md-12">
      <div class="list-group list-group-tree well">
        <div class="tree">
         <li>
          <span class="parent project_name{{$project_id}}"> {{$project_name}} <a class="account_group" data-value="{{$project_id}}" data-name="project"><span class="label label-default" style="color: #000;"><i class="fa fa-plus-circle">&nbsp;</i>ADD</span></a></span>

          <ul> 
            <?php  $acc_collection = collect($rowData); 
            $filtered = $acc_collection->where('parent_id',0);
            $filtered->all();  foreach($filtered as $key=> $value)
            {

              $id=$value['accountclass_id']; ?>
              <li>
                <span class="parent acc_name{{$value['accountclass_id']}}"> {{$value['accountclass_name']}} <a class="account_group" data-value="{{$value['accountclass_id']}}" data-name=""><span class="label label-default" style="color: #000;"><i class="fa fa-plus-circle">&nbsp;</i>ADD</span></a> <a class="accountdelete" data-value="{{$value['accountclass_id']}}" data-parent="{{$value['parent_id']}}"><span class="label label-default" style="color: #F7441E;"><i class="fa fa-minus-circle">&nbsp;</i>Remove</span></a></span>
                <?php 

                $filtered1 = $acc_collection->where('parent_id',$id);
                $filtered1->all(); 
                if(count($filtered1)>0)
                {
                 echo "<ul>";
                 foreach($filtered1 as $key=> $value) 
                 {  
                  ?>
                      <li>
                        <span class="child acc_name{{$value['accountclass_id']}}"> {{$value['accountclass_name']}}<a class="account_group" data-value="{{$value['accountclass_id']}}"><span class="label label-default" style="color: #000;"><i class="fa fa-plus-circle">&nbsp;</i>ADD</span></a><a class="accountdelete" data-value="{{$value['accountclass_id']}}" data-parent="{{$value['parent_id']}}"><span class="label label-default" style="color: #F7441E;"><i class="fa fa-minus-circle">&nbsp;</i>Remove</span></a></span>

                        <?php
                        $parent_id=$value['accountclass_id'];
                         $filtered2 = $acc_collection->where('parent_id',$parent_id);
                $filtered2->all(); 
                      if(count($filtered2)>0)
                        {
                         echo "<ul>";
                         foreach($filtered2 as $key=> $value) 
                         {  
                         ?>
                            <li>
                              <span class="child2 acc_name{{$value['accountclass_id']}}">  {{$value['accountclass_name']}}<a class="account_group" data-value="{{$value['accountclass_id']}}"><span class="label label-default" style="color: #000;"><i class="fa fa-plus-circle">&nbsp;</i>ADD</span></a><a class="accountdelete" data-value="{{$value['accountclass_id']}}" data-parent="{{$value['parent_id']}}"><span class="label label-default" style="color: #F7441E;"><i class="fa fa-minus-circle">&nbsp;</i>Remove</span></a></span>
                              <?php
                              $parent_id=$value['accountclass_id'];
                             $filtered3 = $acc_collection->where('parent_id',$parent_id);
                $filtered3->all(); 
                      if(count($filtered3)>0)
                      {
                               echo "<ul>";
                               foreach($filtered3 as $key=>$value) 
                               {  
                                 ?>
                                  <li>
                                    <span class="child3 acc_name{{$value['accountclass_id']}}">  {{$value['accountclass_name']}}<a class="account_group" data-value="{{$value['accountclass_id']}}"><span class="label label-default" style="color: #000;"><i class="fa fa-plus-circle">&nbsp;</i>ADD</span></a><a class="accountdelete" data-value="{{$value['accountclass_id']}}" data-parent="{{$value['parent_id']}}"><span class="label label-default" style="color: #F7441E;"><i class="fa fa-minus-circle">&nbsp;</i>Remove</span></a></span>
                                    <?php
                                    $parent_id=$value['accountclass_id'];
                                            $filtered4 = $acc_collection->where('parent_id',$parent_id);
                           $filtered4->all(); 
                      if(count($filtered4)>0)
                      {
                                     echo "<ul>";
                                     foreach($filtered4 as $key=>$value) 
                                     {  
                                       ?>
                                        <li>
                                          <span class="child4 acc_name{{$value['accountclass_id']}}">  {{$value['accountclass_name']}}<a class="account_group" data-value="{{$value['accountclass_id']}}"><span class="label label-default" style="color: #000;"><i class="fa fa-plus-circle">&nbsp;</i>ADD</span></a><a class="accountdelete" data-value="{{$value['accountclass_id']}}" data-parent="{{$value['parent_id']}}"><span class="label label-default" style="color: #F7441E;"><i class="fa fa-minus-circle">&nbsp;</i>Remove</span></a></span>
                                          <?php
                                          $parent_id=$value['accountclass_id'];
                                           $filtered5 = $acc_collection->where('parent_id',$parent_id);
                                           $filtered5->all(); 
                                    if(count($filtered5)>0)
                                            {
                                           echo "<ul>";
                                           foreach($filtered5 as $key=>$value) 
                                           {  
                                            ?>
                                              <li>
                                                <span class="child5 acc_name{{$value['accountclass_id']}}">  {{$value['accountclass_name']}}<a class="account_group" data-value="{{$value['accountclass_id']}}"><span class="label label-default" style="color: #000;"><i class="fa fa-plus-circle">&nbsp;</i>ADD</span></a><a class="accountdelete" data-value="{{$value['accountclass_id']}}" data-parent="{{$value['parent_id']}}"><span class="label label-default" style="color: #F7441E;"><i class="fa fa-minus-circle">&nbsp;</i>Remove</span></a></span>
                                                <?php
                                                $parent_id=$value['accountclass_id'];
                                               $filtered6 = $acc_collection->where('parent_id',$parent_id);
                                           $filtered6->all(); 
                                    if(count($filtered6)>0)
                                            {
                                            echo "<ul>";
                                                 foreach($filtered6 as $key=> $value) 
                                                 {  
                                                   ?>
                                                    <li>
                                                      <span class="child6 acc_name{{$value['accountclass_id']}}">  {{$value['accountclass_name']}}<a class="account_group" 
                                                        data-value="{{$value['accountclass_id']}}"><span class="label label-default" style="color: #000;"><i class="fa fa-plus-circle">&nbsp;</i>ADD</span></a><a class="accountdelete" data-value="{{$value['accountclass_id']}}" data-parent="{{$value['parent_id']}}"><span class="label label-default" style="color: #F7441E;"><i class="fa fa-minus-circle">&nbsp;</i>Remove</span></a></span>
                                                        <?php
                                                        $parent_id=$value['accountclass_id'];
                                                           $filtered7 = $acc_collection->where('parent_id',$parent_id);
                                                               $filtered7->all(); 
                                                               if(count($filtered7)>0)
                                                                 {
                                                         echo "<ul>";
                                                         foreach($filtered7 as $key=> $value) 
                                                         {  
                                                           ?>
                                                            <li>
                                                              <span class="child7 acc_name{{$value['accountclass_id']}}"> {{$value['accountclass_name']}}<a class="account_group" data-value="{{$value['accountclass_id']}}"><span class="label label-default" style="color: #000;"><i class="fa fa-plus-circle">&nbsp;</i>ADD</span></a><a class="accountdelete" data-value="{{$value['accountclass_id']}}" data-parent="{{$value['parent_id']}}"><span class="label label-default" style="color: #F7441E;"><i class="fa fa-minus-circle">&nbsp;</i>Remove</span></a></span>
                                                              <?php
                                                              $parent_id=$value['accountclass_id'];
                                                        $filtered8 = $acc_collection->where('parent_id',$parent_id);
                                                               $filtered8->all(); 
                                                               if(count($filtered8)>0)
                                                                 {
                                                               echo "<ul>";
                                                               foreach($filtered8 as $key=> $value) 
                                                               {    ?>
                                                                  <li>
                                                                    <span class="child8 acc_name{{$value['accountclass_id']}}">  {{$value['accountclass_name']}}</span>

                                                                  </li>
                                                                  <?php 

                                                                }
                                                                echo "</ul>";           

                                                              } ?>
                                                            </li>
                                                            <?php 
                                                          }
                                                          echo "</ul>";           

                                                        } ?>
                                                      </li>
                                                      <?php 

                                                    }
                                                    echo "</ul>";           

                                                  } ?>
                                                </li>
                                                <?php 
                                              }
                                              echo "</ul>";           

                                            } ?>
                                          </li>
                                        <?php 
                                      }
                                      echo "</ul>";           

                                    } ?>
                                  </li>
                                <?php 
                              }
                              echo "</ul>";           

                            } ?>
                          </li>
                        <?php 
                      }
                      echo "</ul>";           

                    }
                    echo "</li>";
                 
                } echo "</ul>"; } ?>

                <?php  echo "</li>"; }  ?>;
              </ul>
            </li>

          </div>


        </div>

      </div>
    </div>
  </div>




</div>



</div>
<button type="button" class="btn btn-info btn-lg open_modal" data-toggle="modal" style="display:none;" data-target="#myModal">Open Modal</button>	
<div class="modal fade" id="myModal" role="dialog">
  <div class="modal-dialog" style="width:50%;">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add Level</h4>
      </div>
      <div class="modal-body">
       <div class="form-group  row" > 
        <label for="Job Order No" class=" control-label col-md-4 text-left">
         Project Level
       </label>
       <div class="col-md-6">
        <input type="hidden" class="parent_id" name="parent_id">
        <input type="text" name="accountclass_name" class="form-control accountclass_name">
      </div> 
      <div class="col-md-2">

      </div>
    </div>
  </div>
  <div class="modal-footer">
   <button type="button" class="btn btn-success add_account"  data-dismiss="modal">Add</button>
   <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
 </div>
</div>

</div>
</div>
<!--deepika purpse:delete modal-->
<div class="modal fade" id="deletedetailsModal">
  <div class="modal-dialog" style="width:40%;">
    <div class="modal-content">
     <!--Moda Header-->
     <div class="modal-header">
       <h4 class="modal-title">Delete Confirmation </h4>
       <button type="button" class="close" data-dismiss="modal">&times;</button>
       <input type="hidden" class="soindex" value="">
     </div>
     <!-- Modal Body -->
     <div class="modal-body ">
       <h4>Do You Want to Delete?</h4>
     </div>

     <div style="text-align:center;"> <button type="button" class="btn add savedata" id="savedata"> Confirm</button><button type="button" class="btn cancel" id="canceldata"> Cancel</button></div>
     <!-- Modal footer -->
     <div class="modal-footer">
     </div>

   </div>
 </div>
</div>
<!--end-->

</form>

<style type="text/css">
  #tree-table {

    width: 100%;
    overflow: hidden;
    overflow-x: scroll;
  }
  .treegrid-indent {
    width: 0px;
    height: 16px;
    display: inline-block;
    position: relative;
  }

  .treegrid-expander {
    width: 0px;
    height: 16px;
    color: #07234e;
    display: inline-block;
    position: relative;
    left:-17px;
    cursor: pointer;
  }
  #tree-table th {
    background: #07234e;
    border: transparent;
    color: #fff;
  }
  ol>ol{
    display:none;
  }

</style>
<style>
  .label-default{
    padding: 3px !important;
    font-size: 10px !important;
    font-weight: bold !important;
    background-color: white;
    margin-left: 10px;
  }
  .tree {
    min-height:20px;
    padding:19px;
    margin-bottom:20px;
/*    background-color:#fbfbfb;
border:1px solid #999;*/
-webkit-border-radius:4px;
-moz-border-radius:4px;
border-radius:4px;
-webkit-box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
-moz-box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05)
}
.tree li {
  list-style-type:none;
  margin:0;
  padding:10px 5px 0 5px;
  position:relative;
  overflow: visible !important;
}
.tree li::before, .tree li::after {
  content:'';
  left:-20px;
  position:absolute;
  right:auto
}
.tree li::before {
  border-left:1px solid #777;
  bottom:50px;
  height:100%;
  top:0;
  width:1px
}
.tree li::after {
  border-top:1px solid #777;
  height:20px;
  top:25px;
  width:25px
}
.tree li span {
  -moz-border-radius:5px;
  /*-webkit-border-radius:5px;*/
  border:1px solid #777;
  /*border-radius:5px;*/
  display:inline-block;
  padding:6px 12px;
  text-decoration:none;
  font-size: 12px;
  font-weight: 500;
  text-transform:uppercase;
}
.parent{
  background-color: #1180FF;
  color:white;
  border-radius: 3px;
}
.child{
  background-color: #2589FF;
  color:white;
  border-radius: 3px;
}
.child2{
  background-color: #3391FF;
  color: #fff;
  border: 1px solid #088478 !important;
  border-radius: 3px;
}
.child3{
  background-color: #4B9EFF;
  color: #fff;
  border-radius: 3px;
}
.child4{
  background-color: #5EA8FF;
  color: #fff;
  border-radius: 3px;
}
.child5{
  background-color: #71B3FF;
  color: #fff;
  border-radius: 3px;
}
.child6{
  background-color: #71B3FF;
  color: #fff;
  border-radius: 3px;
}
.child7{
  background-color: #71B3FF;
  color: #fff;
  border-radius: 3px;
}
.child8{
  background-color: #71B3FF;
  color: #fff;
  border-radius: 3px;
}
li> span> a{
  color:white !important;
}
.tree li.parent_li>span {
  cursor:pointer
}
.tree>ul>li::before, .tree>ul>li::after {
  /*border:0*/
}
.tree li:last-child::before {
  height:26px;
}
.tree li.parent_li>span:hover, .tree li.parent_li>span:hover+ul li span {
  /*background:#14b4fc;*/
  border:1px solid #777;
  color:#eee;
}     
</style>
<script type="text/javascript">
  $(function () {
    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
    $('.tree li.parent_li > span').on('click', function (e) {
      var children = $(this).parent('li.parent_li').find(' > ul > li');
      if (children.is(":visible")) {
        children.hide('fast');
        $(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
      } else {
        children.show('fast');
        $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
      }
      e.stopPropagation();
    });
  });
  var dup_chk = true;
  function duplicate_validate()
  {
    var account_code =[];
    var edit_id =[];

    $(".bulk_account_code").each(function(index){
      account_code[index]= $(this).val();
      edit_id[index]= $('.bulk_account_codes_line_id'+index).val();
    });

    $.ajax({
      cache: false,
              url: "{{URL::to('accountbuildcheckname/')}}", //this is your uri
              type: 'GET',
              dataType: 'json',
              async : false,
              data: {account_code : account_code,edit_id : edit_id},
              success: function(response)
              {
                console.log(response);
                if(jQuery.inArray('1', response)) {
                  for(var i = 0; i < response.length; i++){
                    if(response[i] == 1){
                      $('.dup_name'+i).show();
                      $('.dup_name'+i).html('Account Code:'+account_code[i]+' Already Exists');
                      $(".bulk_account_code"+i).val('');
                    }
                  }
                } 
                else
                {
                  var html ="";
                  $('.dup_name').hide();
                  dup_chk = true;
                }
              },
              error: function(xhr, resp, text)
              {
                console.log(xhr, resp, text);
              }
            });
  }

  $("#expList> li").click(function(){


    var $this = $(this);
//console.dir($(this));
if(false == $(this).next().is(':visible')) {
  $('#expList > ol').slideUp(600);
}

var slideup=$(this );
$(this ).next().slideDown(600).children().slideDown();

});

  $('#expList > ol').hide();




  $(document).ready(function() {
   $(".account_group").click(function(){
     var name= $(this).data('name');
     if(name=="project"){
       $(".parent_id").val(0);  
     }else{

       $(".parent_id").val($(this).data('value'));
       var id=$(this).data('value');
       $(".account_head").val($(".acc_name"+id).text().trim().replace('ADD',''));
     }
     $(".open_modal").trigger('click');
   });
// deepika purpose:Add account name
$(".add_account").on('click',function(){

  if($(".accountclass_name").val()=='')
  {
    return false;
  }
  else
  {
    var acc_name=$(".accountclass_name").val();
    var projectid=$(".project_id").val();
    var parent_id=$(".parent_id").val();
    var acc_head=$(".account_head").val();
    var url="{{URL::to('addaccountname')}}/"+acc_name+'/'+parent_id+'/'+projectid;
    $.get(url,function(data){
      var data=data.trim();
      if(data==0)
      {
        notyMsg('info',"Saved Successfully");	
        setTimeout(function(){ 
          location.reload();
        },1500);
      }
    });
  }

});
// deepika purpose remove account name
$(".accountdelete").on('click',function(){
  $("#deletedetailsModal").modal('show');
  var parent_id=$(this).data('parent');
  var acc_id=$(this).data('value');
  $(".savedata").on('click',function(){
    var url="{{URL::to('removeaccountname')}}/"+acc_id+'/'+parent_id;
    $.get(url,function(data){
      var data=data.trim();
      if(data==0)
      {
        notyMsg("info","Deleted Successfully");	
        setTimeout(function(){ 
          location.reload();
        },1500);
      }
    });

  });
});
$("#canceldata").on('click',function(){
  $("#deletedetailsModal").modal('hide');  
});
// delegated handler
$(".list-group-tree").on('click', "[data-toggle=collapse]", function(){
  $(this).toggleClass('in')
  $(this).next(".list-group.collapse").collapse('toggle');

// next up, when you click, dynamically load contents with ajax - THEN toggle
return false;
})

});

</script>

<script>
  $(document).ready(function(){

    var data ="{{\Session::get('j_date_format')}}";
    $(".add_row").on('click',function(){
      var form = $('#acc_code_form');
      form.parsley().destroy();
    });
    $(".add_row").relCopy(data);
    changeclassfields();



    $('.add_row').click(function(){
      var rowCount = $('.acc_codes_table tbody tr').length;
      var index = rowCount - 1;
      changeclassfields();
      $('.bulk_active'+index).val('Yes').change();
    });
    /*Save Function*/
    $('#savestatus').val(''); 
    $(document).on('click','.saveform',function(){
      var btnval		= $(this).val();

      if(btnval == 'APPLYCHANGES')
        var savestatus = 'APPLY CHANGES';
      else(btnval == 'SAVE')
        var savestatus = 'SAVE';

      $('#savestatus').val(savestatus);
      $('.submit_type').val("save");
		//alert(btnval);
   var url = "{{ URL::to('accountcodesnewsave') }}"; 
   validationrule('acc_code_form');
   var red_url = "{{ URL::to('accountcodesnew') }}";
   var formdata	= $('#acc_code_form').serialize();
   var form = $('#acc_code_form');
   if(btnval != 'APPLYCHANGES'){ 
               //  alert("45");
               form.parsley().validate();
               var form = $('#acc_code_form');
               form.parsley().validate();
               console.log(form.parsley().isValid());
               if(form.parsley().isValid()){
                //  alert("fgdf");
//if(dup_chk==true){
//            $(".ajaxLoading").show();
$.post(url,formdata,function(data)	
{
  var status      = data.status;
  var msg         = data.message;
  var id          = data.id;
  var edit_url	= "{{ URL::to('accountcodesedit') }}/"+id;

  if(btnval !='SAVE')
  {                        
    notyMsg(status,msg);
    setTimeout(function(){ 
     window.location.href=edit_url;
   }, 1500);
  }
  else{
    notyMsg(status,msg);
    setTimeout(function(){
      location.reload();
//                                window.location.href=red_url;
}, 1500);
  }
});
                                  //  }
                                }
                              }
                              else
                              {
             // alert("fdsg");
//        $(".ajaxLoading").show();
$.post(url,formdata,function(data)
{
 var status = data.status;
 var msg    = data.message;
 var id     = data.id;
 var edit_url	="{{ URL::to('accountcodescreate') }}/"+id;
 notyMsg(status,msg);
 setTimeout(function(){
   window.location.href=edit_url;
 }, 1500);
});
}
});	
    /*End*/

//$(document).on('click','.remove',function(){
//	var rowCount = $('.acc_codes_table tbody tr').length;
//	if(rowCount > 1){
//	$($(this).closest("tr")).remove();
//	changeclassfields();
//	}
//	else{
//		notyMsg('info',"You Can't Delete Atleast One row should be there");
//	}
//});
$(document).on('click','.remove',function()
{
  var index = $(this).closest('tr').index();
  var rowCount = $('.acc_codes_table tbody tr').length;
  if(rowCount > 1)
  {
   $($(this).closest("tr")).remove();
   removeclassfields();
 }
 else
 {
   notyMsg('info',"You Can't Delete Atleast One row should be there");
 }
});

$(document).on('click','.subacc_btn',function(){
  var id=$(this).data("id");
  addfunction(id);
  $(".subacc_btn").each(function(index){
   if(index!=0)
   {
    $('.bulk_account_code'+index).closest('tr').remove();
  }
  else
  {
    $('.bulk_account_codes_line_id0').val('');
    $('.bulk_account_code0').val('');
    $('.bulk_account_code_meaning0').val('');
    $('.bulk_description0').val('');
    $('.bulk_active0').val('').change();
  }
})
});

var index = $('.clone').closest('tr').index();
changeclassfields();

});
  function addfunction(id){

   $.get("{{ URL::to('subaccountcode') }}/"+id,function(data){

     $('.parent_account_code_meaning').val(data['query'][0].account_code_meaning);
     $('.parent_account_code').val(data['query'][0].account_code);
     $('.child_parent_id').val(data['query'][0].account_codes_line_id);
     $('.acc_parent_id').val(data['query'][0].account_codes_hdr_id);

     var sub=data.sub;

     $.each(sub,function(k,value){

       if(k != "0")
       {
        $('.add_row').trigger('click');

      }
      $('.bulk_account_codes_line_id'+k).val(value.account_codes_line_id);
      $('.bulk_account_code'+k).val(value.account_code);
      $('.bulk_active'+k).val(value.active);
      $('.bulk_parent_id').val(value.parent_class_id);
      $('.bulk_account_code_meaning'+k).val(value.account_code_meaning);
      $('.bulk_description'+k).val(value.description);
      $('.bulk_active'+k).val(value.active).change();

    })

   });


 } 

 function changeClassName(className)
 { 
  $('.' + className).each(function (index)
  {
    if (className == "bulk_line_no")
    {
      $(this).val(index + 1).attr("readonly", 1);
    }

    $(this).removeClass(className + '0');
    $(this).addClass(className + index);
  });
}

function removeClass(className)
{
  var rowCount = $('.acc_codes_table tbody tr').length;
  for(var i=0;i<=rowCount;i++)
  {
    $('.acc_codes_table tbody tr').find('.'+className).removeClass(className+i);
  }
  $('.' + className).each(function (index)
  {
   if (className == "bulk_line_no")
   {
     $(this).val(index + 1).attr("readonly", 1);
   }
   $(this).addClass(className + index);
 });

}
function changeclassfields(){
  changeClassName('bulk_account_codes_line_id');
  changeClassName('bulk_line_no');
  changeClassName('bulk_account_code');
  changeClassName('bulk_account_code_meaning');
  changeClassName('bulk_description');
  changeClassName('bulk_parent_id');
  changeClassName('parent_class_id');
  changeClassName('bulk_active');
  changeClassName('dup_name');
}
function removeclassfields()
{
  removeClass('bulk_account_codes_line_id');
  removeClass('bulk_line_no');
  removeClass('bulk_account_code');
  removeClass('bulk_account_code_meaning');
  removeClass('bulk_description');
  removeClass('bulk_parent_id');
  removeClass('parent_class_id');
  removeClass('bulk_active');
  removeClass('dup_name');

}
</script>
<style>

  #linedesc_table th:nth-child(1),#linedesc_table tbody td:nth-child(1)
  {
    display:none;
  }
  #linedesc_table tbody td:nth-child(2)
  {
    color:#FFF;
  }

  .modal-dialog {
    width: 70% !important;
  }

  .shedule_bom_page_lines_div_left > h5 {
    color: #fff;
    margin-left: 15px;
  }

  .shedule_bom_page_lines_div_left {
    background-clip: border-box;
    background-color: rgba(26, 126, 136, 0.69);
    border: 2px solid gold;
    border-radius: 10px;
    box-shadow: 5px 0 6px 2px #888;
    padding: 5px;
  }

  .modal-dialog {
    margin: 30px auto;
    width: 690px;
  }

  .treeload_refresh > img {
    height: 20px;
    width: 20px;
  }



  /* CSS Tree menu styles */
  .sbox ol.tree {
    padding: 0 0 0 30px;
    /*width: 100%;*/
  }
  .sbox li {
    position: relative;
    margin-left: -15px;
    list-style: none;
  }
  .sbox li input {
    position: absolute;
    left: 0;
    margin-left: 0;
    opacity: 0;
    z-index: 2;
    cursor: pointer;
    height: 1em;
    width: 1em;
    top: 0;
  }
  .sbox li input + ol {
    background: url(images/toggle-small-expand.png) 40px 0 no-repeat;
    margin: -1.600em 0px 8px -44px;
    height: 1em;
  }
  .sbox li input + ol > li {
    display: none;
    margin-left: -14px !important;
    padding-left: 1px;
  }
  .sbox li label {
    background: url(images/folder.png) 15px 1px no-repeat;
    cursor: pointer;
    display: block;
    padding-left: 37px;
    /*background:red;*/
  }

  .sbox li label a {

    color:#FFFFFF;
  }
  .sbox li input:checked + ol {
    background: url(images/toggle-small.png) 40px 5px no-repeat;
    margin: -1.96em 0 0 -44px;
    padding: 1.563em 0 0 80px;
    height: auto;
  }
  .sbox li input:checked + ol > li {
    display: block;
    margin: 8px 0px 0px 0.125em;
  }
  .sbox li input:checked + ol > li:last-child {
    margin: 8px 0 0.063em;
  }



  /*TREE MENUS*/


  .shedule_bom_treesmenus {
    margin-top: 50px;
    padding: 5px 0;
  }


  .shedule_bom_treesmenus {
    height: 245px;
    overflow: auto;
  }

  .bom_header_div_tree.col-md-6 {
    border: 1px solid #999;
    border-radius: 10px;
    box-shadow: 0 0 10px #999;
  }
  .dataTables_filter{display:none;}

</style>
@include('layouts.php_js_validation')
@endsection