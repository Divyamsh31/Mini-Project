const pptxgen = require('pptxgenjs');

let pres = new pptxgen();
pres.layout = 'LAYOUT_16x9';

function addSlide(title, bullets) {
    let slide = pres.addSlide();
    slide.addText(title, { x: 0.5, y: 0.5, w: '90%', h: 1, fontSize: 32, bold: true, color: '003366' });
    let textOpts = { x: 0.5, y: 1.8, w: '90%', h: 4.5, fontSize: 20, color: '333333', bullet: true, lineSpacing: 32 };
    slide.addText(bullets, textOpts);
}

// Slide 1: Title
let s1 = pres.addSlide();
s1.addText("ProScreen ATS", { x: 1, y: 1.5, w: '80%', fontSize: 48, bold: true, align: 'center', color: '003366' });
s1.addText("Accelerating the Hiring Pipeline\nApplicant Tracking & Resume Screening", { x: 1, y: 2.7, w: '80%', fontSize: 24, align: 'center', color: '555555' });
s1.addText("College Miniproject Demonstration", { x: 1, y: 4.5, w: '80%', fontSize: 18, align: 'center', color: '888888' });

// Slide 2: Introduction
addSlide("Introduction", [
    { text: "What is an ATS? An Applicant Tracking System helps companies organize, track, and evaluate candidates." },
    { text: "The Problem:" },
    { text: "Traditional hiring is manual, slow, and prone to error or bias.", options: {indentLevel: 1} },
    { text: "Recruiters spend excessive time screening irrelevant profiles.", options: {indentLevel: 1} },
    { text: "Job seekers lack transparency regarding their application status.", options: {indentLevel: 1} },
    { text: "The Goal: Build an automated platform connecting Job Seekers with Recruiters." }
]);

// Slide 3: Existing vs Solution
addSlide("Existing Systems vs. Our Solution", [
    { text: "Problems with Existing Systems:" },
    { text: "Complex and expensive for small to medium businesses.", options: {indentLevel: 1} },
    { text: "Disconnected workflows (emails vs. tracking spreadsheets).", options: {indentLevel: 1} },
    { text: "Our Proposed Solution (ProScreen ATS):" },
    { text: "A unified, role-based platform (Seeker, Recruiter, Admin).", options: {indentLevel: 1} },
    { text: "Real-time job posting and application tracking.", options: {indentLevel: 1} },
    { text: "Secure, token-based authentication.", options: {indentLevel: 1} }
]);

// Slide 4: Objectives
addSlide("Objectives & Scope", [
    { text: "Job Seekers: Easy profile creation, browsing jobs, quick application." },
    { text: "Recruiters: Efficient job posting, viewing applicant profiles, updating statuses." },
    { text: "Administrators: Centralized oversight of users and metrics." },
    { text: "Scope Limit: Focused on the core workflow of posting, applying, and tracking (ideal MVP)." }
]);

// Slide 5: Tech Stack
addSlide("Technology Stack", [
    { text: "Frontend / UI:" },
    { text: "React.js (Vite environment), Vanilla CSS for styling, React Router.", options: {indentLevel: 1} },
    { text: "Backend / API:" },
    { text: "Java with Spring Boot (RESTful APIs), Spring Data JPA.", options: {indentLevel: 1} },
    { text: "Database: MySQL / PostgreSQL (Relational)." },
    { text: "Tools used: Axios (API integration), Git, Postman." }
]);

// Slide 6: Architecture
addSlide("System Architecture", [
    { text: "Client-Server Model: Clean separation of concerns." },
    { text: "Frontend Layer: Communicates asynchronously using Axios with REST endpoints." },
    { text: "Backend Layer: Spring Boot handles business logic and data persistence." },
    { text: "Database Layer: Manages Users, Resumes/Profiles, Jobs, and Applications." }
]);

// Slide 7: Module 1
addSlide("Module 1 - Authentication & Security", [
    { text: "Secure Login & Registration system." },
    { text: "Three specialized roles: JOBSEEKER, RECRUITER, ADMIN." },
    { text: "Backend validation ensures data integrity constraints (e.g. strong passwords)." },
    { text: "Admin-specific access codes to prevent unauthorized accounts." }
]);

// Slide 8: Module 2
addSlide("Module 2 - Job Seeker Portal", [
    { text: "Dashboard: Overview of active job applications." },
    { text: "Job Board: Browsing open positions." },
    { text: "Profile Management: Keeping details updated for potential employers." },
    { text: "Click-to-Apply: Streamlined process for submitting candidacy." }
]);

// Slide 9: Module 3
addSlide("Module 3 - Recruiter Portal", [
    { text: "Company Profile: Building employer branding." },
    { text: "Job Management: Creating, updating, and managing job postings." },
    { text: "Candidate Pipeline: Viewing applications for specific jobs." },
    { text: "Status Updates: Changing application states (Screening, Hired, Rejected)." }
]);

// Slide 10: Module 4
addSlide("Module 4 - Admin Dashboard", [
    { text: "System Oversight: Highest level of access." },
    { text: "User Management: Viewing registered users across all roles." },
    { text: "Analytics / Metrics: High-level overview of system usage." },
    { text: "Value: Ensures smooth operation and accountability on the platform." }
]);

// Slide 11: Scope
addSlide("Real-World Applications & Future Scope", [
    { text: "Current Use: Deployable in startups or campus placement cells." },
    { text: "Future Scope:" },
    { text: "AI/ML Resume Parsing: Automatically extracting skills from uploaded PDFs.", options: {indentLevel: 1} },
    { text: "Automated Ranking: Matching jobs to resumes algorithmically.", options: {indentLevel: 1} },
    { text: "Email Notifications & Calendar Scheduling.", options: {indentLevel: 1} }
]);

// Slide 12: Conclusion
addSlide("Conclusion", [
    { text: "ProScreen ATS demonstrates a robust full-stack engineering approach." },
    { text: "Provides a responsive, user-friendly experience." },
    { text: "Modular codebase allows for easy scaling and future AI additions." },
    { text: "Thank You! Any Questions?" }
]);

pres.writeFile({ fileName: "ProScreen_ATS_Presentation.pptx" }).then(() => {
    console.log("PPTX created successfully!");
}).catch(err => {
    console.error("Error creating PPTX: ", err);
});
