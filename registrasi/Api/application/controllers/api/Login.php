<?php

use Restserver\Libraries\REST_Controller;

defined('BASEPATH') or exit('No direct script access allowed');


//To Solve File REST_Controller not found
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class login extends CI_Controller
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
        $this->load->model('Login_model', 'login');
    }


    public function index_post()
    {
        $data = [
            'email' => $this->post('email'),
            'password' => $this->post('password')

        ];

        $login = $this->login->post_login($data);

        if ($login) {
            $this->response($login, 200);
        } else {
            $this->response([
                'status' => false,
                'message' => 'Data tidak ditemukan'
            ], 404);
        }
    }
}
