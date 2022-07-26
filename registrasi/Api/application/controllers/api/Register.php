<?php

use Restserver\Libraries\REST_Controller;

defined('BASEPATH') or exit('No direct script access allowed');

//controller ini diperuntukan untuk 
//view_data_kaizen
//view_unit dengan post

//To Solve File REST_Controller not found
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class register extends CI_Controller
{

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
        $this->load->model('register_model', 'register_data');
    }


    public function index_post()
    {
        $data = [
            'first' => $this->post('first'),
            'last' => $this->post('last'),
            'email' => $this->post('email'),
            'password' => $this->post('password')

        ];

        $register_data = $this->register_data->postdata($data);

        if ($register_data) {
            $this->response($register_data, 200);
        } else {
            $this->response([
                'status' => false,
                'message' => 'Data tidak ditemukan'
            ], 404);
        }
    }
}
