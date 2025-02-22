FROM debian:latest

# Install Apache and necessary modules
RUN apt-get update && apt-get install -y \
    apache2 \
    curl \
    media-types \
    && a2enmod proxy \
    && a2enmod proxy_http \
    && a2enmod headers \
    && a2enmod rewrite \
    && a2enmod authz_groupfile \
    && apt-get clean

# Download and install the mime.types file
RUN mkdir -p /etc/apache2/conf \
    && curl -o /etc/apache2/conf/mime.types https://raw.githubusercontent.com/apache/httpd/trunk/docs/conf/mime.types

RUN mkdir -p /var/www/html \
    && chown -R www-data:www-data /var/www/html \
    && chmod -R 755 /var/www/html    

RUN chown -R www-data:www-data /var/log/apache2

# Copy the Apache configuration file
COPY ./httpd.conf /etc/apache2/sites-available/000-default.conf



# Expose port 8081
EXPOSE 8081

# Start Apache in the foreground
CMD ["apache2ctl", "-D", "FOREGROUND"]
