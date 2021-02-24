// global.fetch = require('jest-fetch-mock');
// jest.mock('node-fetch', () => (global.fetch));

//setupJest.js or similar file
require('jest-fetch-mock').enableMocks()
// changes default behavior of fetchMock to use the real 'fetch' implementation and not mock responses
fetchMock.dontMock() 

// global.fetch = require('fetch-mock');
// jest.mock('node-fetch', () => (global.fetch));