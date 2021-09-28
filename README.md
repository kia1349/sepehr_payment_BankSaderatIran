# sepehr_payment_BankSaderatIran
پرداخت اينترنتي نوعي پرداخت الكترونيكي است كه از طريق آن دارندگان كليه كارت‌هاي بانكي عضو شتاب مي‌توانند با استفاده از مشخصات كارت بانكي خود شامل شماره كارت، رمز دوم، تاريخ انقضاء و كد اعتبار سنجي (CVV٢) نسبت به خريد از وب‌سايت‌هاي اينترنتي فروشنده كالا يا خدمات اقدام نمايند.
 درگاه پرداخت اينترنتي سيستم واسط ميان فروشگاه اينترنتي و شبكه بانكي است كه تراكنش‌هاي خريد كاربران اينترنتي يا مشتريان فروشگاه پذيرنده از طريق آن به شبكه بانكي ارسال مي‌گردد.
هدف از ارائه این برنامه رونق بخشيدن به فروش و معاملات صاحبان کسب و کار الکترونیکی و افزايش سرعت انجام معاملات، افزايش امنيت و كنترل در پرداخت وجه از طریق اپلیکیشن اندرویدی است.
همچنین از طریق درگاه پرداخت الکترونیکی سپهر امكان دریافت خدمات متمایزی همچون تسهيم و واريز مبالغ تراكنش‌ها به چند حساب مختلف و با درصدهاي مورد نظر متقاضي امکان پرداخت قبوض خدمات شهری(آب، برق، گاز، تلفن و ...) و خرید شارژ مستقیم سیم کارت های اعتباری اپراتورهای تلفن همراه(همراه اول، ایرانسل و رایتل) امكان ارائه خدمات ويژه نظير دسترسي به ريزتراكنش‌ها به مشتريان خاص از طريق صورتحساب ويژه بانك صادرات ايران میسر می باشد که این امکانات از طریق مراجعه به سایت شرکت پرداخت الکترونیک سپهر(بانک صادرات ایران) https://www.sepehrpay.com/ قابل دریافت می باشد.
برای پرداخت الکترونیکی از طریق اپلیکیشن اندرویدی این امکانات مورد نیاز است:
•	داشتن حساب در بانک صادرات ايران
•	دارا بودن وب سايت آماده جهت فروش اينترنتي (‌چرخه کامل فروش)
•	اخذ نماد اعتماد الکترونيکي از طريق وب سايت www.enamad.ir
•	اخذ کد مالیاتی
•	وجودIP  معتبر (IP Valid) براي وب سايت پذيرنده
•	اخد کد ترمینال منحصر به فرد برای انجام تراکنش
از مزایای این درگاه اینست که هيچ هزينه و كارمزدي از متقاضي (پذيرنده اينترنتي) دريافت نمي گردد.
در این برنامه اندرویدی روش پياده‌سازي و فعال نمودن درگاه پرداخت اينترنتي بانك صادرات ايران ( شركت  psp پرداخت الکترونیک سپهر) برای شما عزیزان به رایگان آموزش داده شده است.
با توجه به الزامات شاپرکی هرگونه تراکنش از طریق اپلیکیشن های اندرویدی باید از طریق Browserها انجام شده و طراحی و انجام فرایند تراکنش در webview مجاز نمی باشد. لذا پیاده سازی روش مورد نظر شاپرک از طریق Browserها  و در نهایت برگشت دادن مشتری از فرم پرداخت (داخل Browser) به برنامه اندروید مستلزم بکارگیری تکنیک Deep Link می باشد.
در بیشتر موارد هدایت مشتری به درگاه پرداخت از Activity و یا  AppCompatActivityصورت میپذیرد. در این روش، برگشت دادن مشتری از فرم پرداخت (داخل Browser) به برنامه اندروید از پیچیدگی کمتری نسبت به برگشت دادن مشتری از فرم پرداخت (داخل Browser) به برنامه اندروید از طریق Fragment دارد و چون بسیاری از دوستان در خصوص برگشت دادن مشتری به برنامه اندروید و فرم Fragment دچار مشکلاتی بوده اند ، در این مثال روش دوم که از طریق Fragment می باشد استفاده شده است.
برای توضیحات بیشتر و درک بهتر برنامه، فیلم آموزش و توضیح این مثال نیز طراحی شده که از طریق لینک زیر قابل دانلود است:
اطلاعات موردنیاز جهت خرید اینترنتی چیست ؟
1.	شماره کارت ( PAN ) : شماره درج شده بر روی کارت می باشد که برای هر کارت منحصر به فرد بوده و  یک شماره 16 رقمی است .
2.	رمز اینترنتی ( PIN2 ) : این عدد همان رمز دوم کارت بوده که می توان با مراجعه به خودپرداز بانک نسبت به فعال سازی و دریافت آن اقدام نمود .
3.	کد اعتبار سنجی دوم ( CVV2 ) :) (Card Verification Value کد 3 یا 4 رقمی حک شده بر رو و یا پشت کارت بانکی است که یکی از ملزومات اساسی در یک خرید اینترنتی به شمار می رود . ( گاهی برای کارت هایی که فاقد CVV2 هستند از 3 رقم آخر شماره کارت ( PAN ) استفاده می گردد . )
4.	تاریخ انقضای کارت :تاریخی است که بر روی کارت های بانکی درج گردیده ومدت اعتبار کارت را نشان می دهد .

معاني برخي اصطلاحات مورد استفاده:
•	پذيرنده : شخص حقيقي يا حقوقي است که با بانک قرارداد بسته است و کارتهاي آن بانک را در فروشگاه خود ميپذيرد.
•	فروشگاه يا سايت پذيرنده : فروشگاه اينترنتي پذيرنده است که در آن محصولات و کالاهاي خود را به صورت اينترنتي عرضه مي کند.
•	درگاه پرداخت اينترنتي : سيستم واسط مابين فروشگاه پذيرنده و شبکه بانک که کاربران اينترنتي يا مشتريان فروشگاه پذيرنده، تراکنشهاي خريد Online خود را  توسط اين سيستم به شبکه بانک ارسال نموده و خريد Online انجام مي دهند.
•	درگاه پرداخت الکترونیک سپهر : نام درگاه پرداخت اينترنتي بانک صادرات ایران می باشد مي باشد که از طریق شرکت psp پرداخت الکترونیک سپهر متعلق به بانک صادرات ایران ارائه میگردد. (درگاه اينترنتي مركزي واريز نقدي)
•	شبکه بانکي : شبکه سيستمهاي Online بانکي که عمليات RealTime و Online بانکي را بر عهده دارند.
•	يکپارچگي سايت پذيرنده و درگاه پرداخت الکترونیک سپهر : اتصال سايت پذيرنده با سيستم درگاه پرداخت الکترونیک سپهر جهت استفاده ار سرويسهاي پرداخت Online بانکي.
•	مشتري يا کاربر : شخصي است که براي خريد اينترنتي از سايت پذيرنده با استفاده از كارت بانكي خود اقدام نموده است.
