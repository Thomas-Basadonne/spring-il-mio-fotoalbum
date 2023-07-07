/* COSTANTI */
const PHOTOS_API_URL = "http://localhost:8080/api/v1/photos";
const CONTACT_API_URL = "http://localhost:8080/api/v1/photos/contact";
const contentDOM = document.getElementById("content");
const contactForm = document.getElementById("contactForm");

let photosData = []; // Variabile globale per conservare i dati delle foto

/* API */
const getPhotos = async () => {
  try {
    const response = await axios.get(PHOTOS_API_URL);
    console.log(response);
    return response.data; // Restituisci la risposta API
  } catch (error) {
    console.log(error);
  }
};

const sendContactMessage = async (email, message) => {
  try {
    const response = await axios.post(CONTACT_API_URL, { email, message });
    console.log(response);
    return response.data; // Restituisci la risposta API
  } catch (error) {
    console.log(error);
  }
};

/* DOM MANIPULATION */
const createPhotosList = (data) => {
  let filteredPhotos = data; // Foto non filtrate

  // Ottenere il valore del termine di ricerca dall'input
  const searchInput = document.getElementById("searchInput").value.trim();

  // Se il termine di ricerca non è vuoto, filtrare le foto per titolo
  if (searchInput !== "") {
    filteredPhotos = data.filter((photo) =>
      photo.titolo.toLowerCase().includes(searchInput.toLowerCase())
    );
  }

  // Filtra le foto per visibilità
  filteredPhotos = filteredPhotos.filter((photo) => photo.visibile);

  if (filteredPhotos.length > 0) {
    let list = "";

    filteredPhotos.forEach((element, index) => {
      list += `
        <div class="col">
        <div class="card h-100 w-100" onclick="showPhotoDetails(${element.id})">
        <img src="${element.url}" class="card-img-top img-fluid h-100 object-fit img-cropped" alt="...">
          <div class="card-body">
              <h5 class="card-title">${element.titolo}</h5>
              <p class="card-text">${element.descrizione}</p>
            </div>
          </div>
        </div>
      `;
    });

    return list;
  } else {
    return '<div class="alert alert-info">Nessuna foto trovata</div>';
  }
};

const filterPhotos = () => {
  loadData(); // Ricarica i dati delle foto e applica i filtri
};

const showPhotoDetails = (id) => {
  axios
    .get(`${PHOTOS_API_URL}/${id}`)
    .then((response) => {
      const photo = response.data;
      // popola la modale con i dettagli della foto
      const photoDetailsElement = document.getElementById("photoDetails");

      // stringa che contiene il nome e la descrizione delle categorie
      let categoriesInfo = "";
      photo.categories.forEach((category) => {
        categoriesInfo += `<p><span class="badge rounded-pill text-bg-primary">${category.name}</span> - <span class="badge rounded-pill text-bg-info text-light">${category.description}</span></p>`;
      });

      photoDetailsElement.innerHTML = `
      <h3><span class="badge text-bg-primary">${photo.titolo}</span></h3>
      <p class="ms-1">${photo.descrizione}</p>
      ${categoriesInfo}
      <img src="${photo.url}" alt="${photo.titolo}" class="img-fluid">
    `;

      // Apri il modale
      const photoModal = new bootstrap.Modal(
        document.getElementById("photoModal")
      );
      photoModal.show();
    })
    .catch((error) => {
      console.log(error);
      // Gestisci eventuali errori durante la chiamata API
    });
};

//metodo gestione submit form
const handleSubmit = (event) => {
  event.preventDefault();
  const email = document.getElementById("emailInput").value;
  const message = document.getElementById("messageInput").value;

  sendContactMessage(email, message)
    .then((response) => {
      console.log(response);
      alert("Messaggio inviato con successo!");
      contactForm.reset();
    })
    .catch((error) => {
      console.log(error);
      alert("Errore durante l'invio del messaggio.");
    });
};

const loadData = async () => {
  const data = await getPhotos();
  photosData = data; // Salva i dati delle foto nella variabile globale

  const visiblePhotos = data.filter((photo) => photo.visibile);
  contentDOM.innerHTML = createPhotosList(visiblePhotos);
};

/* CODICE GLOBALE */

// Carica i dati delle foto all'avvio
getPhotos();
// Carica i dati e gestisci gli eventi del form di contatto
loadData();
contactForm.addEventListener("submit", handleSubmit);
