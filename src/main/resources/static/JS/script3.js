// var campaigns = [
// ];

var editingId = null;

// // فتح المودال
function openModal(id = null) {
  editingId = id;
  document.getElementById("modal-overlay").style.display = "flex";

  if (id) {
    var campaign = campaigns.find(c => c.id === id);

    document.getElementById("c-name").value = campaign.title;
    document.getElementById("c-location").value = campaign.location;
    document.getElementById("c-date").value = campaign.date;
    document.getElementById("c-description").value = campaign.description || "";
  } else {
    resetForm();
  }
}

// // قفل
function closeModal() {
  document.getElementById("modal-overlay").style.display = "none";
  resetForm();
}

// // Reset
function resetForm() {
  document.getElementById("c-name").value = "";
  document.getElementById("c-location").value = "";
  document.getElementById("c-date").value = "";

  document.getElementById("c-description").value = "";

}
