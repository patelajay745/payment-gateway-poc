# payment-gateway

This project was created with [Better Fullstack](https://github.com/Marve10s/Better-Fullstack) using the multi-ecosystem project graph.

## Stack

- Frontend: next (typescript)
- Backend: Java Spring Boot (java)

## Project Structure

```text
payment-gateway/
├── apps/
│   ├── web/         # Frontend application
│   └── server/      # Backend application
└── package.json     # Root scripts for the generated graph
```

## Local Development

Run the frontend and backend in separate terminals so each ecosystem keeps its native watcher and logs.

```sh
bun run dev:web
```

```sh
cd apps/server && ./mvnw spring-boot:run
```

The frontend is configured to call the backend at `http://localhost:8080`. The generated health check targets `http://localhost:8080/health`, and the web environment file contains the matching public server URL.

## Root Scripts

- `dev` starts the web workspace for graph projects.
- `dev:web` starts the frontend workspace.
- `dev:server` starts the generated backend.
- `check:server` runs the backend compile/check lane.
- `test:server` runs backend tests.

## Compatibility Notes

- TypeScript frontends can be generated with Elixir Phoenix backends; Phoenix runs on port 4000 and exposes `/api/health`.
- Astro frontends can be generated with Rust backends; Rust web servers run on port 3000 and expose `/health`.
- Cross-ecosystem graph projects share an HTTP boundary. Framework-specific API clients such as tRPC are not assumed across language boundaries; the scaffold wires the frontend to the backend base URL and health endpoint.
