<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */

//To Solve File REST_Controller not found
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';  

class orders extends CI_Controller {

    use REST_Controller {
        REST_Controller::__construct as private __resTraitConstruct;
    }
    function __construct()
    {
        // Construct the parent class
        parent::__construct(); 
        //$this->load->model('Karyawan_model','karyawan');
          // Construct the parent class
        parent::__construct();
        $this->__resTraitConstruct();

        // Configure limits on our controller methods
        // Ensure you have created the 'limits' table and enabled 'limits' within application/config/rest.php
        $this->methods['index_get']['limit'] = 500; // 500 requests per hour per user/key
        $this->methods['indexs_post']['limit'] = 300; // 100 requests per hour per user/key
        $this->methods['index_put']['limit'] = 300; // 50 requests per hour per user/key
        $this->methods['index_delete']['limit'] = 50; // 50 requests per hour per user/key
        $this->load->model('Apilisorders_model','lis_orders');
    }

    public function index_get(){ 
 
        $data = [
            'startDate' => $this->get('startDate'),
            'endDate' => $this->get('endDate'),
            'REGISTRASI' => $this->get('NoOrder'),  
            'NoRM' => $this->get('NoRM'),  
            'Nama' => $this->get('Nama')  
        ];

        
        $lis_orders = $this->lis_orders->getResult($data);

        if($lis_orders){
            $this->response(  $lis_orders ,200);  
    }else{
        $this->response([
            'status' => false,
            'message' => 'Data tidak ditemukan'
        ],404);  
    }
    }

    //public function index_get(){ 

       // $awal = $_GET['startDate'];
       // $akhir = $_GET['endDate'];
      //  $REGISTRASI = $_GET['NoOrder'];
      //  $NoRM = $_GET['NoRM'];
     //   $Nama = $_GET['Nama']; 

      //  $startDate = $this->get('startDate');
     //   $endDate = $this->get('endDate');
     //   $REGISTRASI = $this->get('NoOrder');
     //   $NoRM = $this->get('NoRM');
    //    $id = $this->get('id');

    //    if($id === null){
            //$karyawan = $this->karyawan->getKaryawan();
      //  }else{
            //$karyawan = $this->karyawan->getKaryawan($id);
     //   }
     

     //   if($karyawan){
     //   $this->response([
     //       'status' => true,
     //       'data' => $karyawan
    //    ],200);  
    //}else{
    //    $this->response([
    //        'status' => false,
     //       'message' => 'Data tidak ditemukan'
    //    ],404);  
    //}
    //}
    
    public function index_delete(){
        $this->response([
            'status' => false,
            'message' => 'Tidak Memiliki Akses'
        ], 404);

        //$id = $this->delete('id');

        //if($id === null){
        //    $this->response([
         //       'status' => false,
         //       'message' => 'Harus ada ID'
         //   ],400); 
        //}else{
         //   if($this->lis_orders->deleteKaryawan($id)>0){
               //ok 
        //       $this->response([
        //        'status' => true,
        //        'id' => $id,
         //       'message' => 'Delete'
        //    ],204); 
        //    }else{
                //id not found
        //        $this->response([
        //            'status' => false,
         //           'message' => 'Id Not Found'
        //        ],404); 
        //    }
        //}
    }

    public function index_post(){
        $data = [
            'NIP' => $this->post('NIP'),
            'NAMA_KARYAWAN' => $this->post('NAMA_KARYAWAN'),
            'ALAMAT' => $this->post('ALAMAT')  
        ];

        if($this->lis_orders->createKaryawan($data) >0){
            $this->response([
                'status' => true,
                'data' => 'Karyawan has been create'
            ],201); 
        }else{
            //id not found
            $this->response([
                'status' => false,
                'message' => 'failed to create new data!'
            ],400); 
        }
    }

    public function index_put(){
        $id = $this->put('id');

        $data = [
            'NIP' => $this->put('NIP'),
            'NAMA_KARYAWAN' => $this->put('NAMA_KARYAWAN'),
            'ALAMAT' => $this->put('ALAMAT')  
        ];

        if($this->lis_orders->updateKaryawan($data,$id) >0){
            $this->response([
                'status' => true,
                'data' => 'Karyawan has been updated'
            ],201); 
        }else{
            //id not found
            $this->response([
                'status' => false,
                'message' => 'failed to update data!'
            ],400); 
        }
    
    }
}