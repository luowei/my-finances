//test( "hello test", function() {
//    ok( 1 == "1", "Passed!" );
//});
//test('assertions', function() {
//    equal( 1 , 1, 'one equals one');
//})
//
//function isEven(val) {
//    return val % 2 === 0;
//}
//test('isEven()', function() {
//    ok(isEven(0), 'Zero is an even number');
//    ok(isEven(2), 'So is two');
//    ok(isEven(-4), 'So is negative four');
//    ok(!isEven(1), 'One is not an even number');
//    ok(!isEven(-7), 'Neither does negative seven');
//    // Fails
//    ok(!isEven(3), 'Three is not an even number');
//})
//
//test('test', function() {
//    propEqual( {}, {}, 'passes, objects have the same content');
//    propEqual( {a: 1}, {a: 1} , 'passes');
//
//    deepEqual( [], [], 'passes, arrays have the same content');
//    deepEqual( [1], [1], 'passes');
//})

//异步测试
//// A custom function
//function ajax(successCallback) {
//    $.ajax({
//        url: 'server.php',
//        success: successCallback
//    });
//}
//
//// Tell QUnit that you expect three assertion to run
////asyncTest('asynchronous test', 3, function() {
//asyncTest('asynchronous test', 3, function() {
//    // Pause the test
////    stop();
//
////    // Tell QUnit that you expect three assertions to run
////    expect(3);
//
//    ajax(function() {
//        ok(true);
//    })
//
//    ajax(function() {
//        ok(true);
//        ok(true);
//    })
//
//    setTimeout(function() {
//        start();
//    }, 2000);
//})

//test mycrypt
test('assertions', function() {
    equal( myEncode('luowei'),'hdvn1tk' , 'test myEncode ok');
    equal( myDecode('hdvn1tk'),'luowei' , 'test myDecode ok');
});

//test base64
test('assertions', function() {
    var encodedStr =  base64encode(utf16to8("rootls.com123456"))

//    alert('decode to hex:'+CharToHex(base64decode(encodedStr)))
    alert('base64Encode to base64:'+encodedStr)

    equal( utf8to16(base64decode(encodedStr)),"rootls.com123456", 'test base64 ok');
});



