<?php

$ch = curl_init();

$secretKey = '6LdCiRcmAAAAAIV9F_h17cxcnx_TSaf16Fn22A7'
$captcha = isset($_POST['recaptcha-response']) && !empty($_POST['recaptcha-response'])? $_POST['recaptcha-response']: '';


curl_setopt_array($ch, [
	CURLOPT_URL =>'https://www.google.com/recaptcha/api/siteverify',
    CURLOPT_POST=> false 
    CURLOPT_POSTFIELDS=> [

          'secret' => $secretKey,
          'response' => $captcha,
          'remoteip'=> $_SERVER['REMOTE_ADDR']


    ],
    CURLOPST_RETURNTRANSFER=> true

]);

$output  = curl_exec($ch);

curl_close($ch);

$json = json_decode(output);

$res = array();

if ($json > sucess){

	$res['sucess'] = true; 	 
	$res['message'] = 'Captcha verificado'; 	 
}

echo json_encode($res);




?>