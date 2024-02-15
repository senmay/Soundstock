# Soundstock

Soundstock is a web application that allows users to trade music stocks based on the popularity of tracks and artists. It utilizes data from various sources such as Spotify, LastFM, and CoinGecko to provide up-to-date information on stock values.

## Features

- **User Registration and Login**: Secure user authentication system allowing for registration, login, and JWT-based session management.
- **Music Stock Trading**: Users can buy and sell stocks associated with music tracks and artists. The stock values fluctuate based on the popularity metrics obtained from Spotify and LastFM.
- **Portfolio Management**: Users can view and manage their portfolios, tracking the performance of their investments in real-time.
- **Market Insights**: Access to real-time data and analytics on music stock performance, including historical data visualization.
- **Integration with Music APIs**: Fetches data from Spotify, LastFM, and CoinGecko to keep the stock values and artist/track information up to date.
- **Security**: Implements robust security configurations, including JWT for secure API access and Spring Security for endpoint protection.

## Technologies Used

- **Spring Boot**: For creating the RESTful backend services.
- **JWT (JSON Web Tokens)**: For secure authentication and session management.
- **Spring Security**: For securing the application endpoints.
- **Docker**: For containerizing the application and its environment.
- **Prometheus and Grafana**: For monitoring the application's performance and visualizing metrics.
- **Spotify, LastFM, and CoinGecko APIs**: For fetching real-time data on music popularity and stock values.

Application still in development
