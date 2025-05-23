/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$( ".slotSelected" ).click(function(event) {
  $(event.target).children("a").click();
});

$( ".slotAvailable" ).click(function(event) {
  $(event.target).children("a").click();
});
