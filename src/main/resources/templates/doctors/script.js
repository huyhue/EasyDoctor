/******************************************
Treehouse Techdegree:
FSJS project 2 - List Filter and Pagination
******************************************/

// I created only two global variables for my functions
const studentList = document.querySelectorAll('li'); // Each student is an li in the index.html
let itemsPerPage = 10;

// Capitalizing the names of the students
const studentNames = document.querySelectorAll('H3');
for (i = 0; i < studentNames.length; i++) {
 studentNames[i].style.textTransform ="capitalize";
}

// Function to show the page we want according to each starting and ending index
const showPage = (list, page) => { // Takes as arguments any given list and which page we want to select
  let startIndex = (page * itemsPerPage) - itemsPerPage; // Ex: For Page 2 * 10 items per page = 20 - 10 items per Page means the starting index is 10
  let endIndex = page * itemsPerPage -1; // Ex: For Page 2 * 10 items per page = 20 - 1 items per Page means the ending index is 19
  for (i = 0; i < list.length; i++) {
    list[i].style.display = 'none'; // For as long as the list goes hide all the elements
    if (i >= startIndex && i <= endIndex ) {
      list[i].style.display = 'block'; // And only show back the ones in the range of the starting and ending indexes we want
    }
  }
};

// Add pagination links
const addPaginationLinks = (list) => {
  removeElementsByClass('pagination') // Remove old elements every time you start running the function so that we don't create endless elements
  let numberOfButtons = Math.ceil(list.length / itemsPerPage); // We need to round up the number of pages/buttons even if we don't get a full page
  let htmlDiv = document.getElementsByClassName('page')[0]; // Get the div with the class of .page to add child elements to (in this case the buttons for the pagination links)
  let newDiv = document.createElement('DIV'); // Creating and nesting elements to hold each pagination link in the webpage
  let newUl = document.createElement('UL');
  newDiv.classList.add("pagination")
  htmlDiv.appendChild(newDiv)
  newDiv.appendChild(newUl)
  for (i = 0; i < numberOfButtons; i++) {
    let li = document.createElement('LI');
    let link = document.createElement('A');
    link.setAttribute("href", "#");
    li.appendChild(link)
    newUl.appendChild(li)
    link.innerText = `${i+1}` // Add numbers to a tags
    if ( i == 0) { // If it is the first/current page
    link.classList.add("active") // Always starts with an "active" state, it's the page we're on
    }
  }
  let links = document.querySelectorAll('a')
  for (i = 0; i < links.length; i++) {
    links[i].addEventListener("click", function(){
      for (i = 0; i < links.length; i++) {
       links[i].classList.remove('active') // Remove the active state for all the pagination links when any of them is clicked
      }
      event.target.classList.add('active') // But add it back to the selected pagination link
      showPage(list, event.target.innerText) // Call the function taking the list items and setting the number of the page according to the text on the buttons
    });
  }
};

// Call the functions
showPage(studentList, 1)
addPaginationLinks(studentList)

// Function to remove the old pagination links when the functions are called as a "restart"
function removeElementsByClass(className){
  var elements = document.getElementsByClassName(className);
  while(elements.length > 0){
      elements[0].parentNode.removeChild(elements[0]);
  }
}

// For the Extra Credit - Add search component to webpage
const headerDiv = document.getElementsByClassName('page-header')[0]; // Get the div with the class of .page to add child elements to (in this case the buttons for the pagination links)
const searchBar = document.createElement('DIV') // Creating new elements to the page
const searchBox = document.createElement('INPUT')
const searchButton = document.createElement('BUTTON')
// Styling of the elements created
searchBar.classList.add("student-search")
stylingElements(searchButton)
searchButton.style.backgroundColor="#4ba6c3"; //aditional button styling
searchButton.style.color="#fff"; // Aditional button styling
stylingElements(searchBox)
searchBox.style.marginRight="5px"; // Aditional search box styling
// Nesting created elements
headerDiv.appendChild(searchBar)
searchBar.appendChild(searchBox)
searchBar.appendChild(searchButton)
searchBox.setAttribute("placeholder", "Search for students...")
searchButton.innerText = "Search"

// Function to find new matches on a certain event
const findMatches = () => {
  let search = searchBox.value.toLowerCase(); // Search works even if input is in all caps
  let results =[] // Create an array to store the matches
  removeElementsByClass('no_results') // Remove old paragraphs we created every time we run the function
  for (i = 0; i < studentList.length; i++) {
    studentList[i].style.display = 'none'; // Hide every element on the list
    if (studentList[i].innerText.indexOf(search) !== -1) {
      results.push(studentList[i]) // Unless they match the search, in that case push the item to the results array
    }
  }
  showPage(results, 1) // Call function
  addPaginationLinks(results) // Call function
  if (results.length == 0) { // If there are no matches
    removeElementsByClass('no_results') // Remove old paragraphs we created every time we run the function
    let parent = document.getElementsByClassName('page')[0]; // Get the parent div
    let newP = document.createElement('P'); // Create a new paragraph for the message
    newP.classList.add("no_results")
    parent.appendChild(newP)
    newP.innerText = "No results found"
    newP.style.marginTop = "50px";
  }
}

searchButton.addEventListener("click", function(){ // Search when clicking button
  findMatches();
});

searchBox.addEventListener("keyup", function(){ // Search on keyup
  findMatches();
});

// Function to style the button and search box
function stylingElements(element){
  element.style.borderRadius="5px";
  element.style.border="1px solid #eaeaea";
  element.style.padding="8px 15px";
  element.style.fontSize="14px";
}
