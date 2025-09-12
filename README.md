# 🛒 Ganesha Webshop

A full-stack webshop project built with modern technologies, developed for a real client and intended for live production use. The focus is on creating a secure, responsive, and containerized application that is easy to scale, extend, and maintain.

This project is **under active development** and intended for real deployment.

<br />

<details>
  <summary>Table of Contents</summary>

- [🌐 Live Demo](#-live-demo)
- [About the Project](#about-the-project)
- [Built With](#built-with)
- [✨ Features](#-features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation Steps](#installation-steps)
  - [Access the Application](#access-the-application)
  - [Stop the Application](#stop-the-application)
- [🧪 To Do](#-to-do)
- [Contact](#contact)

</details>
<br />


### 🌐 Live Demo

The webshop is deployed on [Render.com](https://render.com) and accessible at the following link:

[https://webshop-frontend-wl0h.onrender.com](https://webshop-frontend-wl0h.onrender.com)

> **Note:**  
> The first load may take **up to 30–60 seconds**, especially if the app hasn’t been accessed in a while.  
> This delay is due to [Render's free tier](https://render.com/docs/free#spin-down), which **automatically spins down inactive services** to save resources. When you revisit the site, it needs to "wake up" the backend and frontend containers before responding.
<br />

## About the Project

This project is a **work in progress** full-stack webshop built for a real deployment scenario.  
Once completed, it will include product management, authentication, and a responsive user interface — providing a real-world e-commerce experience.
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

## Getting Started

You can either check out the **[🌐 Live Demo](#-live-demo)** above  
**or** run the project locally by following these steps:

### Prerequisites

This project uses **Docker** for containerized development. To install everything you need, just download `Docker Desktop`:

  - Docker Desktop
    ➡️ https://www.docker.com/products/docker-desktop

### Installation Steps

1. Open a **terminal** and navigate to the directory where you would like to save the repository.
   
2. **Clone the repository** to your machine by executing these commands in your **terminal**:
    ```bash
    git clone https://github.com/o-k-e/ganesha-webshop.git
    cd ganesha-webshop
    ```
   
3. **Configure Environment Variables**

   - To set up your environment variables, copy and rename the .env.example file to .env in the root directory by running the following terminal command:
      - On macOS / Linux:
        ```bash
        cp .env.example .env
        ```

        - On Windows (PowerShell):
        ```bash
        copy .env.example .env
        ```
    - Once copied, you can open the .env file and replace `your_username`, `your_password` with your actual credentials.
      For example:
  
        ```env
        DATABASE_URL=jdbc:postgresql://localhost:5432/ganesha_webshop
        DATABASE_USERNAME=postgres
        DATABASE_PASSWORD=postgres
        JWT_SECRET=uH38!v4zP#cE1sM@9rT$wX2qL*kbZ7oN
        JWT_EXPIRATION=8640000
        ```
        
      ⚠️ **Important:**  
      The `.env` file contains sensitive information (such as database credentials and JWT secrets) and should **never be exposed** in a real production environment. 
      This setup is acceptable **only for local development** or **learning purposes**.  In a production setup, use secure environment variable management tools or secret vaults.

4. **Ensure Docker is Running**
   - Start `Docker Desktop`
     
5. **Build and run the containers**
   - Execute the command in the `backend` folder:
     ```bash
     docker compose up --build
     ```

6. **Access the Application**
   - Open your browser and visit: [http://localhost](http://localhost)
  
7. **Stopping the application**
    
- In your **terminal** press `Ctrl + C`
- If you want to **stop and remove the containers**, but **keep the database data** for future runs, execute:
  ```bash
  docker compose down
  ```
  In this case, the database will **persist** between runs, and your data will still be available next time you start the application.

- If you want to **stop, remove the containers and delete the database data**, execute:
  ```bash
  docker compose down -v
  ```
  In this case, the database and all stored data will be completely removed.

<br />

## ✨ Features

- **Product Browsing**
  - Browse all products (public)
  - View detailed product pages
  - Category-based filtering
  - Pagination and sorting

- **Search & State Management**
  - Global state management with **Zustand**
    - Stores search term, selected category, sort options, page number, and limit
    - Ensures consistent filtering and pagination across components

- **Authentication & Authorization**
  - User registration and login with **JWT-based authentication**
  - Auto token handling with **AuthContext + Axios interceptor**
  - Role-based access control (separate UI for Admin & User)

- **Admin Features**
  - Admin dashboard
  - Create, update, disable products
  - Upload and manage product images

- **Form Handling & Validation**
  - Forms powered by **React Hook Form + Zod**
  - Real-time validation feedback

- **DevOps & Deployment**
  - Fully containerized with **Docker** + NGINX reverse proxy
  - Deployed on **Render.com**
<br />

### Example: Admin - Add New Product

<img width="1030" height="809" alt="Screenshot 2025-09-11 at 00 14 51" src="https://github.com/user-attachments/assets/41770062-ec6a-4ef6-abe1-e14d758ca195" />

<br />

## 🧪 To Do

- **E-commerce Features**
  - Implement product stock quantity
  - Add cart and checkout logic (with order summary & persistence)

- **Admin Panel**
  - Improve responsiveness of the dashboard
  - Add product bulk actions (optional)

- **Authentication**
  - Integrate **OAUTH2** login (e.g. Google)

- **Media Handling**
  - Migrate image storage from local to **Cloudinary**

- **Deployment**
  - Explore deployment to **Vercel** / **Railway**
  - Add CI/CD pipeline (GitHub Actions)

---


## Contact

Feel free to reach out for feedback, collaboration, or questions about this project:

- **Erika Oláhné Klár:**  
  [![GitHub](https://img.shields.io/badge/GitHub-%2312100E.svg?style=for-the-badge&logo=github&logoColor=white)](https://github.com/o-k-e)  [![LinkedIn](https://img.shields.io/badge/LinkedIn-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/erika-olahne-klar/)

