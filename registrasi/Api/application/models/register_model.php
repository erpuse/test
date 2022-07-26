<?php
error_reporting(0);
class register_model extends CI_Model
{



    public function postdata($data)
    {
        $urutan = array();
        foreach ($data as $k => $v) {
            array_push($urutan, $v);
        }

        $first = $urutan[0];
        $last = $urutan[1];
        $email = $urutan[2];
        $password = $urutan[3];
        $tanggal_lengkap = date("Y-m-d H:i:s");

        $jam = strtotime(date("H:i:s"));

        $sql_hitung = "SELECT  count(*) as jumlah FROM user WHERE email='" . $email . "' ";
        $sql_res = $this->db->query($sql_hitung);
        $row_data = $sql_res->row();
        $total = $row_data->jumlah;

        if ($total >= 1) {
            $result['success'] = "0";
            $result['message'] = "Gagal tersimpan. Email sudah terdaftar";
            return $result;
        } else {

            $simpan = "INSERT INTO user  (first_name,last_name,email,password,tanggal) 
            values ('" . $first . "','" . $last . "','" . $email . "','" . md5($password) . "','" . $tanggal_lengkap . "')";
            if ($this->db->query($simpan)) {
                $result['success'] = "1";
                $result['message'] = "success";
                return $result;
            }
        }
    }
}
