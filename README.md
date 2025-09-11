# 🛒 Ganesha Webshop

A full-stack webshop project built with modern technologies, developed for a real client and intended for live production use. The focus is on creating a secure, responsive, and containerized application that is easy to scale, extend, and maintain.

This project is **under active development** and intended for real deployment.

<br />

### 🌐 Live Demo

The webshop is deployed on [Render.com](https://render.com) and accessible at the following link:

[https://webshop-frontend-wl0h.onrender.com](https://webshop-frontend-wl0h.onrender.com)

> **Note:**  
> The first load may take **up to 30–60 seconds**, especially if the app hasn’t been accessed in a while.  
> This delay is due to [Render's free tier](https://render.com/docs/free#spin-down), which **automatically spins down inactive services** to save resources. When you revisit the site, it needs to "wake up" the backend and frontend containers before responding.
<br />
<br />

<img width="1505" height="657" alt="Screenshot 2025-09-10 at 13 00 51" src="https://github.com/user-attachments/assets/e5512ec6-1c86-4f4c-ae17-69147e71dd28" />
<img width="1503" height="693" alt="Screenshot 2025-09-10 at 13 01 18" src="https://github.com/user-attachments/assets/041d57e5-3bf3-4726-a9bf-07e59caf261a" />
<img width="1505" height="506" alt="Screenshot 2025-09-10 at 13 01 38" src="https://github.com/user-attachments/assets/5ad65365-f998-41b7-a32a-a88a99c71c3f" />

<br />

## 🛠️ Built With

- **Languages:**  
  [![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
  [![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)

- **Backend:**  
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)

- **Frontend:**  
  [![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://reactjs.org/)  
  [![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)](https://vitejs.dev/)  
  [![TailwindCSS](https://img.shields.io/badge/TailwindCSS-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white)](https://tailwindcss.com/)

- **Database:**  
  [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)

- **Backend:**  
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)

- **Frontend:**  
  [![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://reactjs.org/)  
  [![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)](https://vitejs.dev/)  
  [![TailwindCSS](https://img.shields.io/badge/TailwindCSS-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white)](https://tailwindcss.com/)

- **Containerization:**  
  [![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)  
  [![NGINX](https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=nginx&logoColor=white)](https://www.nginx.com/)

- **Authentication:** Local storage (planned to add OAUTH2)  
  ![JWT](https://img.shields.io/badge/Auth-JWT-orange?style=for-the-badge&logo=jsonwebtokens)

- **Image Handling:** Local storage (Cloudinary planned for production)  
  ![Local Storage](https://img.shields.io/badge/Image%20Handling-Local%20Storage-blue?style=for-the-badge)
![Cloudinary](https://img.shields.io/badge/Cloudinary-Planned-lightgrey?style=for-the-badge&logo=cloudinary)

- **Version Control:**  
  ![Git](https://img.shields.io/badge/Version%20Control-Git-orange?style=for-the-badge&logo=git)
![GitHub](https://img.shields.io/badge/Repo-GitHub-black?style=for-the-badge&logo=github)

<br />

## Contact

- **Erika Oláhné Klár:**  
  [![GitHub](https://img.shields.io/badge/GitHub-%2312100E.svg?style=for-the-badge&logo=github&logoColor=white)](https://github.com/o-k-e)  [![LinkedIn](https://img.shields.io/badge/LinkedIn-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/erika-olahne-klar/)

<br />

## ✨ Features

- Browse all products (public)
- View product details (public)
- Global state management with Zustand:
Used to store and manage product query parameters such as search term, selected category, sort options, page number, and limit – enabling consistent filtering and pagination across components.
- Role-based UI rendering
- Login authentication (JWT) - (planned to add OAUTH2) 
- Form validation with react-hook-form + Zod
- Auto token handling (AuthContext + interceptor)
- Admin login and role-based access
- Admin dashboard
- Create, update, disable products
- Upload and manage product images
<br />

<img width="1030" height="809" alt="Screenshot 2025-09-11 at 00 14 51" src="https://github.com/user-attachments/assets/41770062-ec6a-4ef6-abe1-e14d758ca195" />

<br />

## 🧪 To Do

- Product stock quantity
- Cart and checkout logic
- Responsive admin dashboard
- Deployment to Vercel / Railway

---

This project is under active development and intended for real deployment.
