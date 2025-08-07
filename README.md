# 🛒 Ganesha Webshop

A full-stack webshop project built with modern technologies, developed for a real client and intended for live production use. The focus is on creating a secure, responsive, and containerized application that is easy to scale, extend, and maintain.

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

## Contact

- **Erika Oláhné Klár:**  
  [![GitHub](https://img.shields.io/badge/GitHub-%2312100E.svg?style=for-the-badge&logo=github&logoColor=white)](https://github.com/o-k-e)  [![LinkedIn](https://img.shields.io/badge/LinkedIn-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/erika-olahne-klar/)


## ✨ Features

- Browse all products (public)
- View product details (public)
- Admin login and role-based access
- Admin dashboard
- Create, update, delete products
- Upload and manage product images
- Category-based filtering
- Pagination (backend ready)
- Login authentication (JWT)
- Form validation with react-hook-form + Zod
- Auto token handling (AuthContext + interceptor)

## 📂 Folder Structure (Frontend)

```
src/
├── components/
├── context/           # AuthContext, Providers
├── hooks/             # useAuth, useUser, useLocalStorage, etc.
├── layouts/           # UserLayout, AdminLayout
├── pages/             # Home, Login, AdminDashboard, etc.
├── services/          # api-client, fileUploader
├── types/             # TypeScript types
├── App.tsx
└── main.tsx
```

## 🚀 Getting Started (Frontend)

1. Install dependencies:
   ```bash
   npm install
   ```

2. Start the dev server:
   ```bash
   npm run dev
   ```

3. Make sure the backend is also running at:
   ```
   http://localhost:8080
   ```

## 🔐 Login Info

Use Postman to log in via:
```
POST http://localhost:8080/api/auth/login
```

Then set token manually in browser:
```js
localStorage.setItem('token', 'YOUR_JWT_TOKEN_HERE');
```

Admin user (example):
```json
{
  "username": "admin",
  "password": "Admin123"
}
```

## 🧪 To Do

- Product search
- Product stock quantity
- Cart and checkout logic
- Responsive admin dashboard
- Full mobile support
- Role-based UI rendering
- Deployment to Vercel / Railway

---

This project is under active development and intended for real deployment.
