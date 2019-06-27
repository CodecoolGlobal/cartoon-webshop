

(function($) {
    $(document).ready(function() {
        var $chatbox = $('.chatbox'),
            $chatboxTitle = $('.chatbox__title'),
            $chatboxTitleClose = $('.chatbox__title__close'),
            $chatboxCredentials = $('.chatbox__credentials');
        $chatboxTitle.on('click', function() {
            $chatbox.toggleClass('chatbox--tray');
        });
        $chatboxTitleClose.on('click', function(e) {
            e.stopPropagation();
            $chatbox.addClass('chatbox--closed');
        });
        $chatbox.on('transitionend', function() {
            if ($chatbox.hasClass('chatbox--closed')) $chatbox.remove();
        });

    });
})(jQuery);

let input = document.getElementById('btn-input');

// Execute a function when the user releases a key on the keyboard
input.addEventListener('keyup', function(event) {
    // Number 13 is the "Enter" key on the keyboard
    if (event.keyCode === 13) {
        // Cancel the default action, if needed
        event.preventDefault();
        // Trigger the button element with a click
        document.getElementById('send-message').click();
    }
});

document.getElementById('send-message').addEventListener('click', conversation);

let messages = [];


function sendMessage() {
    let message = input.value;

    let clientMessage = {
        text: message,
        author: 'client',
        picture: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2FwhYasy2bnB0s-J31rHYVUD5haTxdwm-E7LjT_4AXr2d9DxI'
    }
    messages.push(clientMessage);
    buildCloud();
    document.getElementById('btn-input').value = "";
}

function getMessage() {
    fetch('https://api.chucknorris.io/jokes/random')
        .then(function (res) {
            return res.json()
                .then(function (data) {
                    let serverResponse = {
                        text: data.value,
                        author: 'Chuck_Norris',
                        picture:'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjatrn0Km5fe7T9yM2XdqTQWA9CbiqV83ZKxhvwF-D4K1jcYit'
                    }
                    messages.push(serverResponse)
                }).then(function () {
                    buildCloud();


                })
        })
}

function conversation() {
    sendMessage();
    getMessage();
};




function chatBoxClouds() {
    let element =``;

    for (let chat of messages){
        element += `<div class ='chatbox__body__message chatbox__body__message--${chat['author']}'>
               <img src=${chat['picture']} alt="Picture">
               <div class="clearfix"></div>
               <div class="ul_section_full">
                     <ul class="ul_msg">
                         <li><strong>${chat['author']}</strong></li>
                         <li>${chat['text']}</li>
                     </ul>
               </div>
           </div>`
    }
    return element;

}


function buildCloud() {
        let chatBox = document.querySelector('.chatbox__body');

    chatBox.innerHTML = chatBoxClouds();

}
// function createChatCloud(whoIsSpeaking, message) {
//     let chatbody = document.querySelector('.chatbox__body')
//     let div = document.createElement('div')
//
//
//     chatbody.innerHTML =
//
//          `<div class ='chatbox__body__message chatbox__body__message--${whoIsSpeaking}'>
//               <div class="clearfix"></div>
//               <div class="ul_section_full">
//                     <ul class="ul_msg">
//                         <li><strong>${whoIsSpeaking}</strong></li>
//                         <li>${message}</li>
//                     </ul>
//               </div>
//           </div>  `;

   // chatBox.innerHTML = `<div class ='chatbox__body__message chatbox__body__message--${whoIsSpeaking}'>
   //             <div class="clearfix"></div>
   //             <div class="ul_section_full">
   //                   <ul class="ul_msg">
   //                       <li><strong>${whoIsSpeaking}</strong></li>
   //                       <li>${message}</li>
   //                   </ul>
   //             </div>
   //         </div>  `;
// }
// }