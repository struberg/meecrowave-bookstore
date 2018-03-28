FROM hotswapagent/tomee
RUN mkdir -p /extra_class_path
COPY ./target/bookstore.war ${DEPLOYMENT_DIR}
ENV JAVA_OPTS="-XXaltjvm=dcevm -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n -javaagent:/opt/hotswap-agent/hotswap-agent.jar -Dextra.class.path=/extra_class_path"
