import { NextAuthOptions } from "next-auth";
import KeycloakProvider from "next-auth/providers/keycloak";

export const authOptions: NextAuthOptions = {
    providers: [
        KeycloakProvider({
            clientId: process.env.KEYCLOAK_CLIENT_ID || "visaflow-app",
            clientSecret: process.env.KEYCLOAK_CLIENT_SECRET || "secret",
            issuer: process.env.KEYCLOAK_ISSUER || "http://localhost:18080/realms/visaflow-realm",
        }),
    ],
    callbacks: {
        async jwt({ token, account }) {
            if (account) {
                token.accessToken = account.access_token;
                token.refreshToken = account.refresh_token;
            }
            return token;
        },
        async session({ session, token }) {
            session.accessToken = token.accessToken as string;
            return session;
        },
    },
    pages: {
        signIn: "/auth/login",
    }
};
