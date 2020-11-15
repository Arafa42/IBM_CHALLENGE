# IBM_CHALLENGE
IBM hackathon
------------------------

IBM CHALLENGE : ENERGY SUSTAINABILITY

For this challenge we decided to create a mobile application where the users are able to buy energy in a quick using multiple payment system such as a card scanner, NFC tags or PayPal.

When the application starts up we are welcomed by a splash screen followed by a sign up page. Authentication will be handled by firebase. If we already have an account we will be able to log in by clicking the login button. We also have the ability to log in with our google account.

Once logged in we will see a bottom navigation with the options : charts, merit order, wallet, transaction history, profile.

In our charts tab we will see a top navigation where we can switch between screens which will show us live updated charts of different energy values. The energy values we currently have are water, gas, wind and nuclear energy. In every tab of an energy value we can see three buttons. Once we click on any of these buttons a different chart will be loaded. For instance if we click on the Y button this will mean that the chart of the year ahead forecast will be showed. At the bottom of these pages we have a BUY button which will give us access to buy energy.

Once we click on buy energy we can choose the amount of energy we want to buy. Obviously we can’t buy more than the provided energy at that time which will change because we work with live updating data. After choosing an amount of energy to buy we can proceed to our payment methods.

In our page of payment options we can choose between PayPal or NFC. Once PayPal is selected we have the possibility to pay using our visa, master card or using a card scanner with the camera of our phone. If we choose NFC we have to scan an NFC tag to proceed payment. But due to security issues we protected the NFC payment option with a pin code that you can modify in the settings options in our profile page. In both ways once payment is completed our transactions will be stored in a list in the transaction history page. After completion of our payment we can take a look at our wallet page.

In our wallet page we can see that we can deposit money in our application using PayPal. Once we complete a payment the total amount will be withdrawn from our wallet.

We also have a merit order page which will show you a bar chart of our merit order. This always has to go from small to large, so we wrote a simple algorithm for this feature. 

Last but not least we have our profile page where we can view our profile data. We also have a settings option here where we can change the pin code of our NFC payment option our load our consumption profile. This is just an excel file with data of your yearly energy usage. Once this is loaded successfully you will be able to compare the energy you will be buying in the future (and in the buy energy page) so you know exactly how much energy you used in that specific day of the year so you don’t buy more than you need and can save your energy consumption. 
