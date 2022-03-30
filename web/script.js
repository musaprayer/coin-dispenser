
function clearError(){
document.getElementById('errorDisplay').style.display = 'none';
}
function getCoinsChange() {

  var amount = document.getElementById('amount').value;
  var coinDeno = document.getElementById('coinDeno').value;

  console.log((amount == '') );
  console.log(amount % 1);
  console.log(isNaN(amount) );
  amount % 1 ? console.log("Not Integer") : console.log("Integer");
  //alert(isNaN(Number.isInteger(+amount)));
  if ((amount == '') || isNaN(amount) || amount % 1)
  {
    document.getElementById('errorDisplay').style.display = 'block';
  } else {

  var flickerAPI = "http://localhost:8080/coindispenser/getCoinChange?amount=" + amount;

  $.ajax({
    type: "POST",
    url: "http://localhost:8080/coindispenser/getCoinChange",
    data: JSON.stringify({ "amount": amount, "coinDeno" : coinDeno }),
    contentType: "application/json",
    success: function (data) {
      var displayData = "";
      displayData = '<h4> ' + data.totalNumber + " Total number of coins denomination</h4> <br/>";
      data.coins.forEach(function(coin) {
                           displayData += coin +' <br/>';
                       });
      var priceDesc = document.getElementById('displayCoins') ;
      priceDesc.innerHTML = displayData;
      console.log("post results " + data.totalNumber  );
    },
    error: function (result, status) {
      console.log("err "+ result);
      var errors = "";

      result.responseJSON.forEach(function(error) {
          errors += error.errorMessage + ' \n';
      });
      document.getElementById('errorDisplay').innerText = errors
       document.getElementById('errorDisplay').style.display = 'block';
    }
  });

      }
    };

