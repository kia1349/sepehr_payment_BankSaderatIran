<?php

    $MerchantID = $_GET['MerchantID'];
	$Amount = $_GET['Amount'];
	$Authority = $_GET['Authority'];

	$resultpay='';
//	$newURL2 = 'aryaclub://eshop.aryaclub.com?key=transaction_id&level=1&result=';
	$newURL2 = 'aryaclub://eshop.aryaclub.com?level=1&result=';

        if ($_SERVER["REQUEST_METHOD"] != "POST")
        {
            $_POST +=$_GET;
        }



        if( isset($_POST['respcode']) && $_POST['respcode'] == '0' )
        {

            $params ='digitalreceipt='.$_POST['digitalreceipt'].'&Tid='.$_POST['terminalid'];
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, 'https://sepehr.shaparak.ir:8081/V1/PeymentApi/Advice');
            curl_setopt($ch, CURLOPT_POSTFIELDS, $params);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            $res2 = curl_exec($ch);
            curl_close($ch);
            $result = json_decode($res2,true);

            if (strtoupper($result['Status']) == 'OK') {

                if( floatval($result['ReturnId']) == floatval($_POST['amount']) ){

                    /*/Payment verfied and OK ! */

                    $referenceId = $_POST['digitalreceipt'];
                    $res = 'پرداخت شما کامل شده است';
                    $msg = 'کد پیگیری : '.$referenceId;
                    
                    
                    //me add for show sepehr pay information to user
                    $referenceId = $_POST['digitalreceipt'];
                    $invoiceid = $_POST['invoiceid'];
                    $tracenumber = $_POST['tracenumber'];
                    $rrn = $_POST['rrn'];
                    $msg = "پرداخت شما با موفقیت انجام شد<br/> کد سفارش : $invoiceid <br/> کد پیگیری: $tracenumber<br/> کد پیگیری درگاه: $rrn<br/> شناسه یکتا: $referenceId";

                    $flag = true;
					$resultpay = 'OK1,'.$res.'  '.$msg;
					
		            $responseData = array('success' => 'OK1', 'message' => $res, 'tracking' => $msg, 'value' => 1);

                    header('Location: '.$newURL2.json_encode($responseData));
                    die();
                    die;

                }else
                {

                    $res = 'پرداخت انجام نشد';
                    $msg = 'مبلغ سفارش و مبلغ واریز شده برابر نیست';
					$resultpay = 'OK2,'.$msg;
	    			// 	echo 'OK2,'.$msg;

		            $responseData = array('success' => 'OK2', 'message' => $res, 'tracking' => $msg, 'value' => 1);

                    header('Location: '.$newURL2.json_encode($responseData));
                    die();
                    die;
        
                }

            }else
            {
                switch($result['ReturnId'])
                {
                    case '-1' : $res = 'تراکنش پیدا نشد';break;
                    case '-2' : $res = 'تراکنش قبلا Reverse شده است';break;
                    case '-3' : $res = 'خطا عمومی';break;
                    case '-4' : $res = 'امکان انجام درخواست برای این تراکنش وجود ندارد';break;
                    case '-5' : $res = 'آدرس IP پذیرنده نامعتبر است';break;
                    default  : $res = 'خطای ناشناس : '.$result['ReturnId'];break;

                }
                $msg = 'پرداخت انجام نشد';
                // wrong payment
				$resultpay = 'ERROR,'.$msg;
	            // echo 'ERROR,'.$msg;

	            $responseData = array('success' => 'ERROR', 'message' => $res, 'tracking' => $msg, 'value' => 1);

                header('Location: '.$newURL2.json_encode($responseData));
                die();
                die;

            }


        }
        else{
            $msg = 'برگشت ناموفق از درگاه';
            $res = 'پرداخت انجام نشد';

			$resultpay = 'CANCELL,0,'.$msg;
// 			echo 'CANCELL,0,'.$msg;

            $responseData = array('success' => 'CANCELL', 'message' => $res, 'tracking' => $msg, 'value' => 1);

            header('Location: '.$newURL2.json_encode($responseData));
            die();
            die;


        }

//   $logPath = __DIR__.'/../public/paymentlogs.txt';
//   $logfile = fopen($logPath, 'w');
//   fwrite($logfile, $resultpay);
//   fclose($logfile);




?>

