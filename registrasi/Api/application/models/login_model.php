<?php
error_reporting(0);
class Login_model extends CI_Model
{

    public function post_login($data)
    {
        $urutan = array();
        foreach ($data as $k => $v) {
            array_push($urutan, $v);
        }

        $email = $urutan[0];
        $password = $urutan[1];
        $tanggal_lengkap = date("Y-m-d H:i:s");
        $sql = "SELECT * FROM user WHERE  email='$email' ";
        $row_data = $this->db->query($sql);
        $result = array();
        if ($row_data->num_rows() === 1) {
            $data_view = $row_data->row();
            $data = md5($password);
            if ($data_view->password == $data) {




                array_push($result, array(
                    'id'               => $data_view->id,
                    'email'             => $data_view->email
                ));
                return array("value" => 1, "result" => $result);
            } else {
                return array("value" => 0, "Read" => 0);
            }
        } else {
            return array("value" => 0, "Read" => 1);
        }
    }
}
