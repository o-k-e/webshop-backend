# Ganesha Webshop

This is a real-world webshop project, developed using a modern full-stack architecture. It is under active development for a real client and intended for production deployment.

## 🛠️ Built With

- **Frontend:** React + TypeScript + Tailwind CSS
- **Backend:** Java + Spring Boot
- **Database:** PostgreSQL
- **Authentication:** JWT (JSON Web Token)
- **Image Handling:** Local storage (Cloudinary planned for production)
- **Containerization:** Docker (optional)
- **Version Control:** Git (GitHub)

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

## 🧑‍💻 Technologies

| Area        | Tech Stack                       |
|-------------|----------------------------------|
| Frontend    | React, TypeScript, Tailwind CSS  |
| Backend     | Spring Boot (Java)               |
| Database    | PostgreSQL                       |
| Auth        | JWT                              |
| Styling     | Tailwind CSS                     |
| Validation  | Zod + react-hook-form            |
| Deployment  | Docker + CI/CD (planned)         |

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
