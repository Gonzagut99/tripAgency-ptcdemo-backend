# 📚 Manual de Autenticación JWT - PTC Agency API

## Descripción General

La API de PTC Agency utiliza **JWT (JSON Web Tokens)** para la autenticación. Este sistema proporciona:

- ✅ **Access Token**: Token de corta duración (15 minutos) para acceder a endpoints protegidos
- ✅ **Refresh Token**: Token de larga duración (7 días) para renovar el access token
- ✅ **Gestión de Sesiones**: Control de sesiones activas en múltiples dispositivos
- ✅ **Token Rotation**: Los refresh tokens se rotan en cada renovación para mayor seguridad

---

## 🔗 Base URL

```
http://localhost:8090/ptc/api/v1
```

---

## 🔐 Endpoints de Autenticación

### 1. Login - Iniciar Sesión

**Endpoint:** `POST /auth/login`

**Descripción:** Autentica un usuario y devuelve los tokens JWT.

**Request:**
```json
{
  "email": "usuario@ejemplo.com",
  "password": "contraseña123"
}
```

**Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 900,
  "refreshExpiresIn": 604800,
  "expiresAt": "2025-11-30T15:30:00",
  "user": {
    "id": 1,
    "email": "usuario@ejemplo.com",
    "userName": "Juan Pérez",
    "isActive": true
  }
}
```

**Errores posibles:**
| Código | Descripción |
|--------|-------------|
| 400 | Datos de solicitud inválidos (email o contraseña vacíos) |
| 401 | Credenciales inválidas |

**Ejemplo con fetch (JavaScript/TypeScript):**
```typescript
const login = async (email: string, password: string) => {
  const response = await fetch('/ptc/api/v1/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ email, password }),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message);
  }

  const data = await response.json();
  
  // Guardar tokens
  localStorage.setItem('accessToken', data.accessToken);
  localStorage.setItem('refreshToken', data.refreshToken);
  
  return data;
};
```

---

### 2. Refresh Token - Renovar Tokens

**Endpoint:** `POST /auth/refresh`

**Descripción:** Genera nuevos tokens usando el refresh token.

**Request:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 900,
  "refreshExpiresIn": 604800,
  "expiresAt": "2025-11-30T15:45:00",
  "user": {
    "id": 1,
    "email": "usuario@ejemplo.com",
    "userName": "Juan Pérez",
    "isActive": true
  }
}
```

**⚠️ Importante:** El refresh token anterior se invalida después de usarse (Token Rotation).

**Ejemplo con fetch:**
```typescript
const refreshTokens = async () => {
  const refreshToken = localStorage.getItem('refreshToken');
  
  const response = await fetch('/ptc/api/v1/auth/refresh', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ refreshToken }),
  });

  if (!response.ok) {
    // Refresh token expirado - redirigir a login
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    window.location.href = '/login';
    throw new Error('Session expired');
  }

  const data = await response.json();
  
  // Actualizar tokens
  localStorage.setItem('accessToken', data.accessToken);
  localStorage.setItem('refreshToken', data.refreshToken);
  
  return data;
};
```

---

### 3. Logout - Cerrar Sesión

**Endpoint:** `POST /auth/logout`

**Headers requeridos:**
```
Authorization: Bearer {accessToken}
```

**Response (200 OK):**
```json
{
  "message": "Sesión cerrada exitosamente",
  "success": true
}
```

**Ejemplo:**
```typescript
const logout = async () => {
  const accessToken = localStorage.getItem('accessToken');
  
  await fetch('/ptc/api/v1/auth/logout', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${accessToken}`,
    },
  });

  // Limpiar tokens locales
  localStorage.removeItem('accessToken');
  localStorage.removeItem('refreshToken');
  
  window.location.href = '/login';
};
```

---

### 4. Logout All - Cerrar Todas las Sesiones

**Endpoint:** `POST /auth/logout-all`

**Headers requeridos:**
```
Authorization: Bearer {accessToken}
```

**Descripción:** Cierra todas las sesiones activas del usuario en todos los dispositivos.

**Response (200 OK):**
```json
{
  "message": "Todas las sesiones han sido cerradas exitosamente",
  "success": true
}
```

---

### 5. Get Current User - Obtener Usuario Actual

**Endpoint:** `GET /auth/me`

**Headers requeridos:**
```
Authorization: Bearer {accessToken}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "email": "usuario@ejemplo.com",
  "userName": "Juan Pérez",
  "isActive": true
}
```

**Ejemplo:**
```typescript
const getCurrentUser = async () => {
  const accessToken = localStorage.getItem('accessToken');
  
  const response = await fetch('/ptc/api/v1/auth/me', {
    headers: {
      'Authorization': `Bearer ${accessToken}`,
    },
  });

  if (!response.ok) {
    throw new Error('Failed to get user');
  }

  return response.json();
};
```

---

### 6. Get Sessions - Obtener Sesiones Activas

**Endpoint:** `GET /auth/sessions`

**Headers requeridos:**
```
Authorization: Bearer {accessToken}
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "createdAt": "2025-11-30T10:00:00",
    "expiresAt": "2025-11-30T10:15:00",
    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)...",
    "ipAddress": "192.168.1.1",
    "isCurrent": true,
    "isActive": true
  },
  {
    "id": 2,
    "createdAt": "2025-11-29T08:00:00",
    "expiresAt": "2025-11-29T08:15:00",
    "userAgent": "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0)...",
    "ipAddress": "192.168.1.2",
    "isCurrent": false,
    "isActive": true
  }
]
```

---

## 🛡️ Uso del Token en Peticiones Protegidas

Todas las peticiones a endpoints protegidos deben incluir el header de autorización:

```
Authorization: Bearer {accessToken}
```

**Ejemplo:**
```typescript
const fetchProtectedResource = async (endpoint: string) => {
  const accessToken = localStorage.getItem('accessToken');
  
  const response = await fetch(`/ptc/api/v1${endpoint}`, {
    headers: {
      'Authorization': `Bearer ${accessToken}`,
      'Content-Type': 'application/json',
    },
  });

  if (response.status === 401) {
    // Token expirado - intentar refresh
    try {
      await refreshTokens();
      return fetchProtectedResource(endpoint); // Reintentar
    } catch {
      // Refresh falló - redirigir a login
      window.location.href = '/login';
    }
  }

  return response.json();
};
```

---

## 🔄 Interceptor de Axios Recomendado

```typescript
import axios from 'axios';

const api = axios.create({
  baseURL: '/ptc/api/v1',
});

// Interceptor para añadir token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Interceptor para manejar token expirado
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshToken = localStorage.getItem('refreshToken');
        const response = await axios.post('/ptc/api/v1/auth/refresh', {
          refreshToken,
        });

        const { accessToken, refreshToken: newRefreshToken } = response.data;
        
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', newRefreshToken);

        originalRequest.headers.Authorization = `Bearer ${accessToken}`;
        return api(originalRequest);
      } catch (refreshError) {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        window.location.href = '/login';
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);

export default api;
```

---

## ⏱️ Tiempos de Expiración

| Token | Duración | Milisegundos |
|-------|----------|--------------|
| Access Token | 15 minutos | 900,000 |
| Refresh Token | 7 días | 604,800,000 |

---

## 🚨 Códigos de Error Comunes

| Código | Mensaje | Descripción |
|--------|---------|-------------|
| 400 | Datos de solicitud inválidos | Faltan campos requeridos o formato inválido |
| 401 | No autorizado | Token inválido, expirado o credenciales incorrectas |
| 403 | Acceso denegado | No tienes permisos para este recurso |
| 404 | No encontrado | El recurso no existe |
| 500 | Error interno | Error del servidor |

---

## 📋 Estructura del Token JWT

**Header:**
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

**Payload (Access Token):**
```json
{
  "sub": "usuario@ejemplo.com",
  "userId": 1,
  "email": "usuario@ejemplo.com",
  "roles": ["ROLE_USER"],
  "userName": "Juan Pérez",
  "type": "access",
  "iss": "ptc-agency",
  "iat": 1732976400,
  "exp": 1732977300
}
```

**Payload (Refresh Token):**
```json
{
  "sub": "usuario@ejemplo.com",
  "userId": 1,
  "type": "refresh",
  "iss": "ptc-agency",
  "iat": 1732976400,
  "exp": 1733581200
}
```

---

## 🔒 Recomendaciones de Seguridad

1. **Nunca expongas tokens en URLs** - Usa headers de autorización
2. **Almacenamiento seguro** - Considera usar httpOnly cookies para producción
3. **HTTPS obligatorio** - Siempre usa HTTPS en producción
4. **Manejo de errores** - Implementa logout automático en errores 401
5. **Refresh proactivo** - Renueva el token antes de que expire
6. **Limpieza al logout** - Elimina todos los tokens almacenados

---

## 📖 Documentación Swagger

Accede a la documentación interactiva en:
```
http://localhost:8090/ptc/api/swagger-ui.html
```

OpenAPI JSON:
```
http://localhost:8090/ptc/api/api-docs
```
