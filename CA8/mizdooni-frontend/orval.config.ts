import { defineConfig } from 'orval';

 

 export default defineConfig({

   mizdooni: {

    input: {
        validation: true,
        target: './api-specification.json'
    },

    output: {
        target: './mizdooni.ts',
        schemas: './contracts',
        workspace: 'src/',
        client: 'axios',
        mode: 'split',
        mock: false,
        baseUrl: 'https://backend:443'
    }

   },

 });