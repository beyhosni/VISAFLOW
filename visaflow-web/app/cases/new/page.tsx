"use client";

import { AppNavbar } from "@/components/app-navbar";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import api from "@/lib/api";
import { useRouter } from "next/navigation";
import { useState } from "react";
import { Loader2 } from "lucide-react";

const formSchema = z.object({
    petitionerName: z.string().min(2, "Name must be at least 2 characters."),
    beneficiaryName: z.string().min(2, "Name must be at least 2 characters."),
});

export default function CreateCasePage() {
    const router = useRouter();
    const [isLoading, setIsLoading] = useState(false);

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            petitionerName: "",
            beneficiaryName: "",
        },
    });

    async function onSubmit(values: z.infer<typeof formSchema>) {
        setIsLoading(true);
        try {
            const res = await api.post("/cases", values);
            const newCaseId = res.data.id;
            router.push(`/cases/${newCaseId}`);
        } catch (error) {
            console.error(error);
            // Show toast error
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <div className="flex flex-col min-h-screen">
            <AppNavbar />
            <div className="flex-1 container py-8 max-w-2xl">
                <Card>
                    <CardHeader>
                        <CardTitle>Start a New Application</CardTitle>
                        <CardDescription>
                            Enter the names of the Petitioner (US Citizen/LPR) and the Beneficiary (Intending Immigrant).
                        </CardDescription>
                    </CardHeader>
                    <CardContent>
                        <Form {...form}>
                            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                                <FormField
                                    control={form.control}
                                    name="petitionerName"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Petitioner Name</FormLabel>
                                            <FormControl>
                                                <Input placeholder="John Doe" {...field} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="beneficiaryName"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Beneficiary Name</FormLabel>
                                            <FormControl>
                                                <Input placeholder="Jane Smith" {...field} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <Button type="submit" className="w-full" disabled={isLoading}>
                                    {isLoading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                                    Create Application
                                </Button>
                            </form>
                        </Form>
                    </CardContent>
                </Card>
            </div>
        </div>
    );
}
