<?php

        $EcommerceUrl = $_GET['EcommerceUrl'];
        $MerchantID = $_GET['MerchantID'];
        $Amount = $_GET['Amount'];
        $Description = $_GET['Description'];
        $Email = $_GET['Email'];
        $Mobile = $_GET['Mobile'];
        $Amount = (int)$Amount * 10;
    //	$newURL1 = 'aryaclub://eshop.aryaclub.com?key=transaction_id&level=0&result=';
    	$newURL1 = 'aryaclub://eshop.aryaclub.com?level=0&result=';

        if ($Amount>=1000) {

            if($MerchantID !="" && $EcommerceUrl !="" ){
                $invoiceNumber = time();
        		$terminal = $MerchantID;
                $CallbackURL = $EcommerceUrl.'SepehrPayGet.php'.'?Amount='.$Amount.'&MerchantID='.$MerchantID;
                $params ='terminalID='.$terminal.'&Amount='.$Amount.'&callbackURL='.urlencode($CallbackURL).'&invoiceID='.$invoiceNumber;
                if (!empty($Mobile)){
                    $params .= '&CellNumber=' . $Mobile;
    
                }
                $ch = curl_init();
                curl_setopt($ch, CURLOPT_URL, 'https://sepehr.shaparak.ir:8081/V1/PeymentApi/GetToken');
                curl_setopt($ch, CURLOPT_POSTFIELDS, $params);
                curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
                curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                $res = curl_exec($ch);
                curl_close($ch);



                if($res)
                {
                    $res = json_decode($res,true);
                    if($res['Status'] == '0')
                    {
                        echo '<form id="paymentUTLfrm" action="https://sepehr.shaparak.ir:8080" method="POST">
        						<input type="hidden" id="TerminalID" name="TerminalID" value="'.$terminal.'">
        						<input type="hidden" id="token" name="token" value="'.$res['Accesstoken'].'">
        						<input type="hidden" id="getMethod" name="getMethod" value="1">
        						</form>
        						<script>
        						function submitmabna() {
        						document.getElementById("paymentUTLfrm").submit();
        						}
        						window.onload=submitmabna;
        						</script>';
                        die;
                        die();


                    }else
                    {
                    // echo 'ERROR,'. '?????? ???? ???????? ????????  ???? ?????? :'.$res['Status'];

                    $res = '???????????? ?????????? ??????';
                    $msg =  '?????? ???? ???????? ????????  ???? ?????? :'.$res['Status'];

		            $responseData = array('success' => 'ERROR1', 'message' => $res, 'tracking' => $msg, 'value' => 0);

                    header('Location: '.$newURL1.json_encode($responseData));
                    die();
                    die;


                    }
                }
                else
                {
                    // echo 'ERROR,'. '???????? 8081 ???? ???????? ?????? ???????? ?????? !';
                    
                    $res = '???????????? ?????????? ??????';
                    $msg = '???????? 8081 ???? ???????? ?????? ???????? ?????? !';

		            $responseData = array('success' => 'ERROR2', 'message' => $res, 'tracking' => $msg, 'value' => 0);

                    header('Location: '.$newURL1.json_encode($responseData));
                    die();
                    die;
                    
                    
                }
            }
        }else{
            // echo '???????? ?????????? ???????? ???? ???????? ???????? ??????';
         
     
            $res = '???????????? ?????????? ??????';
            $msg = '???????? ?????????? ???????? ???? ???????? ???????? ??????';

            $responseData = array('success' => 'ERROR3', 'message' => $res, 'tracking' => $msg, 'value' => 0);

            header('Location: '.$newURL1.json_encode($responseData));
            die();
            die;

            // $ret=$msg;
         

             }


?>
